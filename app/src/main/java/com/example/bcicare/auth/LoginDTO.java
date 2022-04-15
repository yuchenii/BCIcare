package com.example.bcicare.auth;

import com.google.gson.annotations.SerializedName;

/**
 * @author yuchen
 */
public class LoginDTO {

    /**
     * access_token
     */
    @SerializedName("access_token")
    private String accessToken;
    /**
     * refresh_token
     */
    @SerializedName("refresh_token")
    private String refreshToken;

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
}
