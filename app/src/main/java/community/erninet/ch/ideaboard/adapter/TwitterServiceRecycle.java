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

/**
 * This class provides functionality to get the tweets for a specific twitter-user. It uses Retrofit
 * for REST-calls and gson for automatic conversion of JSON-Objects to POJOs. The resulting POJOs
 * are automatically bound to a listView (via TwitterAdapter )and displayed in the UI. Authentication
 * with Twitter works via OAuth2 application-only authentication
 */
public class TwitterServiceRecycle {

    /**
     * This callback is executed as soon as retrofit gets a result from the Twitter status-API.
     * Depending on whether the request was successful, either the function success or failure is
     * called by retrofit.
     */
    private final Callback tweetCallback = new Callback<ArrayList<ErniTweet>>() {
        /**
         * Method that is executed, if twitter could successfully deliver tweets from a specific user.
         * The methods arguments include the resulting list of Tweets and the plain http-response.
         * The only thing that the method does, is updating the adapter with the new tweets, such that
         * they are displayed in the connected listView.
         * @param tweets ArrayList of ErniTweet objects that have been retrieved from Twitter
         * @param response Plain http response
         */
        @Override
        public void success(ArrayList<ErniTweet> tweets, Response response) {
            //clear the adapter from all previous elements
            adapter.clear();
            //add all retrieved tweets to the adapter
            adapter.addAll(tweets);
        }

        /**
         * If the tweets from Twitter could not be retrieved successfully, retrofit returns an object
         * that contains detailed information about the error. We do nothing else, than displaying the
         * attached error-message in the AndroidLog.
         *
         * @param retrofitError Object that contains the error message
         */
        @Override
        public void failure(RetrofitError retrofitError) {
            RetrofitError err = retrofitError;
            Log.d("Error: ", retrofitError.getResponse().toString());
        }
    };
    private String username = ""; //member that stores the twitter-username, from which tweets will be retrieved
    /**
     * This callback is executed as soon as retrofit gets the results from the twitter authentication.
     * Depending on whether the request was successful, either the function success or failure is
     * called by retrofit.
     */
    private final Callback authCallback = new Callback<Authenticated>() {
        /**
         * The response from twitter is automatically converted to an Authenticated POJO. The
         * response contains an access token and the type of the token. Iff the auth-object is available
         * and the token-type is bearer (according to OAuth2-standard), we call the method that
         * performs an async-call to the twitter API and retrieves the tweets.
         * @param auth Object containing authentication details
         * @param response plain http-response
         */
        @Override
        public void success(Authenticated auth, Response response) {
            //check whether auth-data available and of type bearer
            if (auth != null && auth.getTokenType().equals("bearer")) {
                //call Twitter-API to retrieve tweets
                service.getTwitterStream("Bearer " + auth.getAccessToken(), username, 50, tweetCallback);
            }
        }

        /**
         * If the tweets from auth-details could not be retrieved successfully, retrofit returns an object
         * that contains detailed information about the error. We do nothing else, than displaying the
         * attached error-message in the AndroidLog.
         *
         * @param retrofitError Object that contains the error message
         */
        @Override
        public void failure(RetrofitError retrofitError) {
            RetrofitError err = retrofitError;
            Log.d("Error: ", retrofitError.getResponse().toString());
        }
    };
    private TwitterAdapterRecycle adapter = null; //Adapter object, that will connect the results to a listView
    private TwitterAPI service; //instance member of the API-interface
    //variables that are needed to generate the authentication-string
    private String base64Encoded = "";
    private String combined = "";
    private TypedString typedString = null;

    /**
     * The constructor sets up the gson-converter and the retrofit REST-adapter. Additionally, the
     * necessary variables for Twitter OAuth2 authentication are generate.
     */
    public TwitterServiceRecycle() {
        //create a new gson object
        Gson gson = new GsonBuilder()
                //specify, that the attributes are all lowercase and with underscores
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        //setup the retrofit rest-adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                //specify the endpoint and the gson-converter for automatic json-conversion
                .setEndpoint("https://api.twitter.com").setConverter(new GsonConverter(gson))
                .build();
        //tell the adapter that we are going to use the TwitterAPI-interface, which actually describes
        //the different web services
        this.service = restAdapter.create(TwitterAPI.class);

        /**
         * Each Twitter developer can register his application. Some data can be retrieved without actually telling
         * which (registered, personal) user is calling the API. The simple OAuth2 proccess needs two keys,
         * concatenated by a colon.
         */
        combined = "G96CXPu2KYlK5SnOA8wTDBcGH" + ":" + "8Re1vtSMO7GAgYzMNpGUVUOXDjhm3SCrkBDnrbiXZ9hqE8nopO";
        // string needs to be base64-encoded
        base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        //tell twitter, which authentication method we are going to use
        typedString = new TypedString("grant_type=client_credentials");
    }

    /**
     * This method sets the ArrayAdapter to be used when a list of tweets is retrieved.
     *
     * @param adapter Object of type TwitterAdapter
     */
    public void setAdapter(TwitterAdapterRecycle adapter) {
        this.adapter = adapter;
    }

    /**
     * Tha API will retrieve tweets of the specified twitter-user
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method starts an async call to Twitter, to retrieve the tweets. At the beginning, the authentication service is called.
     * In case of a successful authentication, the service to get all the tweets is automatically called.
     */
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

