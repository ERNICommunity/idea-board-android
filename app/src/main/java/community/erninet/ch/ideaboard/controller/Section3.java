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


public class Section3 extends Fragment {


    private final Callback tweetCharlieCallback = new Callback<ArrayList<ErniTweet>>() {
        @Override
        public void success(ArrayList<ErniTweet> tweets, Response response) {
            adapterCharlie.addAll(tweets);
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
                service.getTwitterStream("Bearer " + auth.getAccessToken(), "charliesheen", 50, tweetCharlieCallback);
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
    private TwitterAdapter adapterCharlie;

    public Section3() {
        // Required empty public constructor
    }

    public static Section3 newInstance() {
        Section3 fragment = new Section3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct the data source
        ArrayList<ErniTweet> tweetArray = new ArrayList<ErniTweet>();
        // Create the adapter to convert the array to views
        adapterCharlie = new TwitterAdapter(getActivity(), tweetArray);
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
        return inflater.inflate(R.layout.fragment_section3, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) getActivity().findViewById(R.id.lvTweetsSheen);
        listView.setAdapter(adapterCharlie);

        // Concatenate the encoded consumer key, a colon character, and the encoded consumer secret
        String combined = "G96CXPu2KYlK5SnOA8wTDBcGH" + ":" + "8Re1vtSMO7GAgYzMNpGUVUOXDjhm3SCrkBDnrbiXZ9hqE8nopO";

        // Base64 encode the string
        String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        TypedString typedString = new TypedString("grant_type=client_credentials");

        service.authorizeUser("Basic " + base64Encoded, String.valueOf(typedString.length()), typedString, authCallback);
    }
}
