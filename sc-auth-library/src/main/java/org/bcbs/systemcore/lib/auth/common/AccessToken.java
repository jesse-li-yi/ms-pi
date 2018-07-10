package org.bcbs.systemcore.lib.auth.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class AccessToken {

    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
    private Date issueTime;
    private Date expireTime;
    private TokenAuthentication tokenAuthentication;

    public void eraseAuthentication() {
        this.tokenAuthentication = null;
    }

    // Getter & setter.
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public TokenAuthentication getTokenAuthentication() {
        return tokenAuthentication;
    }

    public void setTokenAuthentication(TokenAuthentication tokenAuthentication) {
        this.tokenAuthentication = tokenAuthentication;
    }
}
