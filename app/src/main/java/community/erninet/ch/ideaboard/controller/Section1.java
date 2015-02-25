package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.TwitterAPI;
import community.erninet.ch.ideaboard.adapter.TwitterAdapter;
import community.erninet.ch.ideaboard.model.Authenticated;
import community.erninet.ch.ideaboard.model.ErniTweet;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedString;


public class Section1 extends Fragment {

    private final Callback tweetErniCallback = new Callback<ArrayList<ErniTweet>>() {
        @Override
        public void success(ArrayList<ErniTweet> tweets, Response response) {
            adapterErni.addAll(tweets);
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
    private final Callback authCallback = new Callback<Authenticated>() {
        @Override
        public void success(Authenticated auth, Response response) {
            if (auth != null && auth.getTokenType().equals("bearer")) {
                // Authenticate API requests with bearer token
                service.getTwitterStream("Bearer " + auth.getAccessToken(), "ERNI", 50, tweetErniCallback);
                /*service.getTwitterStream("Bearer " + auth.getAccessToken(), "ERNI", 50,tweetDaniCallback);
                service.getTwitterStream("Bearer " + auth.getAccessToken(), "ERNI", 50,tweetCharlieCallback);*/
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
    private TwitterAPI service;
    private TwitterAdapter adapterErni;


    public Section1() {
        // Required empty public constructor
    }

    // using the factory method here to create the fragment is best practice
    // allows you to pass stuff to fragments using the setArguments method (commented out here)
    public static Section1 newInstance() {
        Section1 fragment = new Section1();

        /* below  is just an example how to set arguments for the fragment, which could be passed in through arguments to the newInstance method
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        myFragment.setArguments(args);
*/

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct the data source
        ArrayList<ErniTweet> tweetArray = new ArrayList<ErniTweet>();
        // Create the adapter to convert the array to views
        adapterErni = new TwitterAdapter(getActivity(), tweetArray);
        // Attach the adapter to a ListView

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.twitter.com").setConverter(new GsonConverter(gson))
                .build();
        this.service = restAdapter.create(TwitterAPI.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section1, container, false);


    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) getActivity().findViewById(R.id.lvTweetsErni);
        listView.setAdapter(adapterErni);
        /*listView = (ListView) findViewById(R.id.lvTweetsDani);
        listView.setAdapter(adapterDani);
        listView = (ListView) findViewById(R.id.lvTweetsSheen);
        listView.setAdapter(adapterCharlie);*/

        // Concatenate the encoded consumer key, a colon character, and the encoded consumer secret
        String combined = "G96CXPu2KYlK5SnOA8wTDBcGH" + ":" + "8Re1vtSMO7GAgYzMNpGUVUOXDjhm3SCrkBDnrbiXZ9hqE8nopO";

        // Base64 encode the string
        String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        TypedString typedString = new TypedString("grant_type=client_credentials");

        service.authorizeUser("Basic " + base64Encoded, String.valueOf(typedString.length()), typedString, authCallback);
    }
}
