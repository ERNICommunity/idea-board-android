package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
    private final Callback tweetDaniCallback = new Callback<ArrayList<ErniTweet>>() {
        @Override
        public void success(ArrayList<ErniTweet> tweets, Response response) {
            adapterDani.addAll(tweets);
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
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private TwitterAPI service;
    private TwitterAdapter adapterErni;
    private TwitterAdapter adapterDani;
    private TwitterAdapter adapterCharlie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);


        // Set up the navigation drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Construct the data source
        ArrayList<ErniTweet> tweetArray = new ArrayList<ErniTweet>();
        // Create the adapter to convert the array to views
        adapterErni = new TwitterAdapter(this, tweetArray);
        adapterDani = new TwitterAdapter(this, tweetArray);
        adapterCharlie = new TwitterAdapter(this, tweetArray);
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
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.lvTweetsErni);
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // update the main content by replacing fragments

        switch (position) {

            case 1: // Section 2
                transaction.replace(R.id.container, Section2.newInstance())
                        .commit();
                break;
            case 2: // Section 3
                transaction.replace(R.id.container, Section3.newInstance())
                        .commit();
                break;
            default: // Section 1, default as initial position is 0
                transaction.replace(R.id.container, Section1.newInstance())
                        .commit();

        }


    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
