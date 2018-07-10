package org.bcbs.systemcore.lib.auth.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class RefreshToken {

    private String refreshToken;
    private String accessToken;
    private Date issueTime;
    private Date expireTime;
    private TokenAuthentication tokenAuthentication;

    // Getter & setter.
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
