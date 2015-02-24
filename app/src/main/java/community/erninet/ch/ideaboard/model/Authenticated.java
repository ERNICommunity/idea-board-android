package community.erninet.ch.ideaboard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ue65403 on 2015-02-24.
 */
public class Authenticated {
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("access_token")
    private String accessToken;

    public Authenticated(String type, String token) {
        this.tokenType = type;
        this.accessToken = token;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String type) {
        this.tokenType = type;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }
}
