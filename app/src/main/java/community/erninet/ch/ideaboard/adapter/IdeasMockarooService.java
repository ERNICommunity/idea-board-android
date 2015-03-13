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
 This class mocks a service to gather ideas from the backend. It uses the mockaroo API and retrofit for communication. The results are stored in the globals.
 Afterwards an adapter is updated if attached.
 */
public class IdeasMockarooService {

    /**
     * This callback is executed as soon as retrofit gets a result from the Mockaroo-API.
     * Depending on whether the request was successful, either the function success or failure is
     * called by retrofit.
     */
    private final Callback ideaCallback = new Callback<ArrayList<Idea>>() {
        /**
         * Method that is executed, if the mocked ideas got fetched sucessfully.
         * The methods arguments include the resulting list of Ideas and the plain http-response.
         * The only thing that the method does, is updating the adapter with the new tweets, such that
         * @param response Plain http response
         */
        @Override
        public void success(ArrayList<Idea> ideas, Response response) {
            updateList(ideas);
        }

        /**
         * If the ideas from Mockaroo could not be retrieved successfully, retrofit returns an object
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


    private IdeaAdapter adapter = null; //Adapter object, that will connect the results to a recyclerView
    private IdeaMockaroo service; //instance member of the API-interface
    private Application mApp; //application context to store the results to globals


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
        //tell the adapter that we are going to use the Mockaroo-interface, which actually describes
        //the different web services
        this.service = restAdapter.create(IdeaMockaroo.class);
    }

    /**
     * This method sets the RecyclerVieewAdapter to be used when a list of ideas is retrieved.
     *
     * @param adapter Object of type ideaAdapter
     */
    public void setAdapter(IdeaAdapter adapter) {
        this.adapter = adapter;
    }


    /**
     * This method starts an async call to Mockaroo (or some other backend), to retrieve the ideas.
     */
    public void getIdeas() {
        if (service != null) {
            //API key, max 15 ideas, schema name@mockaroo, callback
            service.getMockarooIdeas("79a80540", 15, "ideaMock", ideaCallback);
        }
    }

    /**
     * This is a simple, local mock-service to add a new idea. The idea is added LOCALLY
     * to the global-arrayList, which stores the ideas
     *
     * @param newIdea
     */
    public void createIdea(Idea newIdea) {
        //Read the ideas
        ArrayList<Idea> ideas = ((Globals) mApp).getAllIdeas();
        //append the new one
        ideas.add(newIdea);
        //call the method, that updates the adapter
        updateList(ideas);
    }

    /**
     * store an arrayList of Ideas in the globals and update the RecycleViewAdapter
     * @param ideas
     */
    private void updateList(ArrayList<Idea> ideas) {
        ((Globals) mApp).setAllIdeas(ideas);
        if (adapter != null) {
            adapter.clear();
            adapter.addAll(((Globals) mApp).getAllIdeas());
        }
    }


    /**
     * Actuall mock service interface to describe the service. This is the core of any retrofit
     * implementation!!
     */
    public interface IdeaMockaroo {
        @Headers({"Content-Type: application/json"})
        //POST request to mockaroo
        @POST("/api/generate.json")
        void getMockarooIdeas(
                //API key to be used
                @Query("key") String key,
                //Max items retrieved
                @Query("count") int count,
                //schema name
                @Query("schema") String schema,
                //and of course a callback since we want to stay async
                Callback<ArrayList<Idea>> ideaCallback);
    }

}

