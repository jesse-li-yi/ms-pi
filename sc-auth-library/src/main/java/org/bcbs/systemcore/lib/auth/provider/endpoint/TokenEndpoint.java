package org.bcbs.systemcore.lib.auth.provider.endpoint;

import org.bcbs.systemcore.lib.auth.annotation.AuthEndpoint;
import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.AuthResponse;
import org.bcbs.systemcore.lib.auth.common.exception.UnsupportedGrantTypeException;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.ClientDetailsService;
import org.bcbs.systemcore.lib.auth.provider.TokenRequest;
import org.bcbs.systemcore.lib.auth.provider.token.TokenGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AuthEndpoint
public class TokenEndpoint {

    private ClientDetailsService clientDetailsService;
    private TokenGranter tokenGranter;
    private AuthServerTokenService authServerTokenService;

    @Autowired
    public TokenEndpoint(ClientDetailsService clientDetailsService, TokenGranter tokenGranter,
                         AuthServerTokenService authServerTokenService) {
        this.clientDetailsService = clientDetailsService;
        this.tokenGranter = tokenGranter;
        this.authServerTokenService = authServerTokenService;
    }

    @PostMapping(value = "/auth/token")
    @PreAuthorize(value = "hasAuthority('token')")
    public ResponseEntity<AuthResponse> accessToken(Authentication clientAuthentication,
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
