package community.erninet.ch.ideaboard.adapter;

import android.util.Base64;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.model.Authenticated;
import community.erninet.ch.ideaboard.model.ErniTweet;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedString;

public class TwitterService {

    private final Callback tweetCallback = new Callback<ArrayList<ErniTweet>>() {
        @Override
        public void success(ArrayList<ErniTweet> tweets, Response response) {
            adapter.clear();
            adapter.addAll(tweets);
        }

        /**
         * On errors inside the framework, en error message is created
         *
         * @param retrofitError
         */
        @Override
        public void failure(RetrofitError retrofitError) {
            RetrofitError err = retrofitError;

            Log.d("Error: ", retrofitError.getResponse().toString());
        }
    };
    private String username = "";
    private final Callback authCallback = new Callback<Authenticated>() {
        @Override
        public void success(Authenticated auth, Response response) {
            if (auth != null && auth.getTokenType().equals("bearer")) {
                // Authenticate API requests with bearer token
                service.getTwitterStream("Bearer " + auth.getAccessToken(), username, 50, tweetCallback);
            }
        }

        /**
         * On errors inside the framework, en error message is created
         *
         * @param retrofitError
         */
        @Override
        public void failure(RetrofitError retrofitError) {

            Log.d("Error: ", "Auth Error");
        }
    };
    private TwitterAdapter adapter = null;
    private TwitterAPI service;
    private String base64Encoded = "";
    private String combined = "";
    private TypedString typedString = null;

    public TwitterService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.twitter.com").setConverter(new GsonConverter(gson))
                .build();
        this.service = restAdapter.create(TwitterAPI.class);

        // Concatenate the encoded consumer key, a colon character, and the encoded consumer secret
        combined = "G96CXPu2KYlK5SnOA8wTDBcGH" + ":" + "8Re1vtSMO7GAgYzMNpGUVUOXDjhm3SCrkBDnrbiXZ9hqE8nopO";
        // Base64 encode the string
        base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        typedString = new TypedString("grant_type=client_credentials");
    }

    public void setAdapter(TwitterAdapter adapter) {
        this.adapter = adapter;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void getTweets() {
        if (adapter != null && username != "") {
            service.authorizeUser("Basic " + base64Encoded, String.valueOf(typedString.length()), typedString, authCallback);
        }
    }

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

}

