package community.erninet.ch.ideaboard.adapter;

import android.app.Application;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.application.Globals;
import community.erninet.ch.ideaboard.model.Idea;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * This class provides functionality to get the tweets for a specific twitter-user. It uses Retrofit
 * for REST-calls and gson for automatic conversion of JSON-Objects to POJOs. The resulting POJOs
 * are automatically bound to a listView (via TwitterAdapter )and displayed in the UI. Authentication
 * with Twitter works via OAuth2 application-only authentication
 */
public class IdeasMockarooService {

    /**
     * This callback is executed as soon as retrofit gets a result from the Twitter status-API.
     * Depending on whether the request was successful, either the function success or failure is
     * called by retrofit.
     */
    private final Callback ideaCallback = new Callback<ArrayList<Idea>>() {
        /**
         * Method that is executed, if twitter could successfully deliver tweets from a specific user.
         * The methods arguments include the resulting list of Tweets and the plain http-response.
         * The only thing that the method does, is updating the adapter with the new tweets, such that
         * they are displayed in the connected listView.
         * @param response Plain http response
         */
        @Override
        public void success(ArrayList<Idea> ideas, Response response) {
            updateList(ideas);
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


    private IdeaAdapter adapter = null; //Adapter object, that will connect the results to a listView
    private IdeaMockaroo service; //instance member of the API-interface
    private Application mApp;


    /**
     * The constructor sets up the gson-converter and the retrofit REST-adapter. Additionally, the
     * necessary variables for Twitter OAuth2 authentication are generate.
     */
    public IdeasMockarooService(Application app) {

        mApp = app;
        //create a new gson object
        Gson gson = new GsonBuilder()
                //specify, that the attributes are all lowercase and with underscores
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        //setup the retrofit rest-adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                //specify the endpoint and the gson-converter for automatic json-conversion
                .setEndpoint("http://www.mockaroo.com").setConverter(new GsonConverter(gson))
                .build();
        //tell the adapter that we are going to use the TwitterAPI-interface, which actually describes
        //the different web services
        this.service = restAdapter.create(IdeaMockaroo.class);
    }

    /**
     * This method sets the ArrayAdapter to be used when a list of tweets is retrieved.
     *
     * @param adapter Object of type TwitterAdapter
     */
    public void setAdapter(IdeaAdapter adapter) {
        this.adapter = adapter;
    }


    /**
     * This method starts an async call to Twitter, to retrieve the tweets. At the beginning, the authentication service is called.
     * In case of a successful authentication, the service to get all the tweets is automatically called.
     */
    public void getIdeas() {
        if (service != null) {
            service.getMockarooIdeas("79a80540", 15, "ideaMock", ideaCallback);
        }
    }

    public void createIdea(Idea newIdea) {
        ArrayList<Idea> ideas = ((Globals) mApp).getAllIdeas();
        ideas.add(newIdea);
        updateList(ideas);
    }

    private void updateList(ArrayList<Idea> ideas) {
        ((Globals) mApp).setAllIdeas(ideas);
        adapter.clear();
        adapter.addAll(((Globals) mApp).getAllIdeas());
    }


    /**
     * Created by ue65403 on 2015-02-24.
     */
    public interface IdeaMockaroo {
        @Headers({"Content-Type: application/json"})
        @POST("/api/generate.json")
        void getMockarooIdeas(
                @Query("key") String key,
                @Query("count") int count,
                @Query("schema") String schema,
                Callback<ArrayList<Idea>> ideaCallback);
    }

}

