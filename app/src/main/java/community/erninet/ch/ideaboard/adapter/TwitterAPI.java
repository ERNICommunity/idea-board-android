package community.erninet.ch.ideaboard.adapter;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.model.Authenticated;
import community.erninet.ch.ideaboard.model.ErniTweet;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedString;

/**
 * Created by ue65403 on 2015-02-24.
 */
public interface TwitterAPI {
    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("/oauth2/token")
    void authorizeUser(
            @Header("Authorization") String authorization,
            @Header("Content-Length") String bodyLength,
            @Body TypedString grantType, Callback<Authenticated> authenticatedCallback);

    @Headers({"Content-Type: application/json"})
    @GET("/1.1/statuses/user_timeline.json")
    void getTwitterStream(
            @Header("Authorization") String authorization,
            @Query("screen_name") String screenName,
            @Query("count") int count, Callback<ArrayList<ErniTweet>> tweetCallback);
}

