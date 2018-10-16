package org.bcbs.systemcore.lib.auth.provider.token.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bcbs.systemcore.lib.auth.common.constraint.RevokeType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RevokeRequest {

    @NotNull(message = "Invalid token revoke type.")
    private RevokeType revokeType;

    @NotEmpty(message = "Invalid phone number.")
    private String phoneNo;

    @NotEmpty(message = "Invalid access token.")
    private String accessToken;

    // Getter & setter.
    public RevokeType getRevokeType() {
        return revokeType;
    }

    public void setRevokeType(RevokeType revokeType) {
        this.revokeType = revokeType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
