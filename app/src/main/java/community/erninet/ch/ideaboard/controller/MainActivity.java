package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.TwitterAdapter;
import community.erninet.ch.ideaboard.model.ErniTweet;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


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

    }

    @Override
    public void onResume() {
        super.onResume();
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
