package org.bcbs.systemcore.lib.auth.provider.endpoint;

import org.bcbs.systemcore.lib.auth.annotation.AuthEndpoint;
import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.AuthResponse;
import org.bcbs.systemcore.lib.auth.common.constraint.GrantType;
import org.bcbs.systemcore.lib.auth.common.constraint.RevokeType;
import org.bcbs.systemcore.lib.auth.common.exception.SignupException;
import org.bcbs.systemcore.lib.auth.common.exception.UnsupportedGrantTypeException;
import org.bcbs.systemcore.lib.auth.provider.AccountDetailsService;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.ClientDetailsService;
import org.bcbs.systemcore.lib.auth.provider.token.TokenGranter;
import org.bcbs.systemcore.lib.auth.provider.token.request.RevokeRequest;
import org.bcbs.systemcore.lib.auth.provider.token.request.SignupRequest;
import org.bcbs.systemcore.lib.auth.provider.token.request.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AuthEndpoint
public class TokenEndpoint {

    private ClientDetailsService clientDetailsService;
    private AccountDetailsService accountDetailsService;
    private TokenGranter tokenGranter;
    private AuthServerTokenService authServerTokenService;

    @Autowired
    public TokenEndpoint(ClientDetailsService clientDetailsService, AccountDetailsService accountDetailsService,
                         TokenGranter tokenGranter, AuthServerTokenService authServerTokenService) {
        this.clientDetailsService = clientDetailsService;
        this.accountDetailsService = accountDetailsService;
        this.tokenGranter = tokenGranter;
        this.authServerTokenService = authServerTokenService;
    }

    @PostMapping(value = "/auth/token")
    @PreAuthorize(value = "hasAuthority('token')")
    public ResponseEntity<AuthResponse> acquireToken(Authentication clientAuthentication,
                                                     @Validated @RequestBody TokenRequest tokenRequest) {
        if (!clientAuthentication.isAuthenticated())
            throw new InsufficientAuthenticationException("The client is not authenticated.");

        ClientDetails clientDetails = this.clientDetailsService.loadClientByClientId(clientAuthentication.getName());
        AccessToken accessToken = this.tokenGranter.grant(clientDetails, tokenRequest);
        if (accessToken == null)
            throw new UnsupportedGrantTypeException(tokenRequest.getGrantType().name());

        accessToken.eraseAuthentication();
        return generateResponse(AuthResponse.success(accessToken));
    }

    @RequestMapping(value = "/auth/check_token")
    @PreAuthorize(value = "hasAuthority('token')")
    public ResponseEntity<AuthResponse> checkToken(@RequestParam("token") String tokenValue) {
        AccessToken accessToken = this.authServerTokenService.getAccessToken(tokenValue);
        accessToken.eraseAuthentication();
        return generateResponse(AuthResponse.success(accessToken));
    }

    @PostMapping(path = "/auth/signup")
    @PreAuthorize(value = "hasAuthority('signup')")
    public ResponseEntity<AuthResponse> signUp(Authentication clientAuthentication,
                                               @Validated @RequestBody SignupRequest signupRequest) {
        UserDetails userDetails = this.accountDetailsService.signup(signupRequest.getPhoneNo(),
                signupRequest.getPassword());
        if (userDetails == null)
            throw new SignupException("Failed to create the new account.");

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGrantType(GrantType.PASSWORD);
        tokenRequest.setPhoneNo(signupRequest.getPhoneNo());
        tokenRequest.setPassword(signupRequest.getPassword());
        return acquireToken(clientAuthentication, tokenRequest);
    }

    @PostMapping(path = "/auth/revoke_token")
    @PreAuthorize(value = "hasAuthority('token')")
    public ResponseEntity<AuthResponse> revokeToken(@Validated @RequestBody RevokeRequest revokeRequest) {
        if (RevokeType.ACCESS_TOKEN.equals(revokeRequest.getRevokeType()))
            this.authServerTokenService.revokeAccessToken(revokeRequest.getAccessToken());
        else if (RevokeType.USER_TOKENS.equals(revokeRequest.getRevokeType()))
            this.authServerTokenService.revokeUserAccessTokens(revokeRequest.getPhoneNo());
        return null;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<AuthResponse> handleException(Exception ex) {
        return generateResponse(AuthResponse.failure(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    private ResponseEntity<AuthResponse> generateResponse(AuthResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-common");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(response, headers, HttpStatus.valueOf(response.getStatus()));
    }
}
