package community.erninet.ch.ideaboard.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.UserBackend;
import community.erninet.ch.ideaboard.application.Globals;
import community.erninet.ch.ideaboard.model.JSONResponseException;
import community.erninet.ch.ideaboard.model.User;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    //storage variable to handle the user-request
    private UserBackend.OnConversionCompleted callHandlerGetUser;
    //error handler to handle errors from the user retrieval
    private UserBackend.OnJSONResponseError errorHandlerUser;


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

        attachUserCallbacks();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!((Globals) getApplication()).isUserLoggedIn()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.newInstance(), "Login").commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // update the main content by replacing fragments

        if (((Globals) getApplication()).isUserLoggedIn()) {

            switch (position) {

                case 1: // Section 2
                    transaction.replace(R.id.container, DiscussAndVoteFragment.newInstance(), "MY_IDEAS")

                            .commit();
                    break;
                case 2: // Section 3
                    transaction.replace(R.id.container, OverviewFragment.newInstance(), "DISCUSS_AND_VOTE")
                            .commit();
                    break;
                case 3:
                    ((Globals) getApplication()).setUserLoggedIn(false);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.newInstance(), "LOGIN")
                            .commit();
                    break;
                default: // Section 1, default as initial position is 0

                    transaction.replace(R.id.container, MyIdeasFragment.newInstance(), "OVERVIEW")
                            .commit();

            }

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

    public void authorizeUser(String username, String pwd) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //again, create an object to call the user-backend
        UserBackend getUser = new UserBackend();
        //attached the specified handlers
        getUser.setListener(callHandlerGetUser);
        getUser.setErrorListener(errorHandlerUser);


        //get user by username and password. the handlers will redirect to either the signup
        //or the mymood, depending on whether the user exists or not
        if (isOnline()) {
            getUser.getUserByPassword(username, pwd);
            //startProgress(getString(R.string.authorize_progress));
            //progress.setTitle(getString(R.string.authorize_progress));
        } else {
            Toast.makeText(
                    getBaseContext(), "No network connection",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check, whether the network connection is available
     *
     * @return True if yes, False if no
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void attachUserCallbacks() {
        //event handler when user could not be loaded
        callHandlerGetUser = new UserBackend.OnConversionCompleted<User>() {
            @Override
            public void onConversionCompleted(User user) {
                //display username
                Log.d("User successfully load", user.getUsername());
                //after authentication of the user, update the mood list
                ((Globals) getApplication()).setUser(user.getUsername());
                ((Globals) getApplication()).setUserLoggedIn(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, MyIdeasFragment.newInstance(), "MY_IDEAS").commit();
            }
        };
        //very important event
        //event handler for loading the user. log that something went wrong
        //and forward the user to the login-page (where he could navigate further to the
        //sign-up page
        errorHandlerUser = new UserBackend.OnJSONResponseError() {
            @Override
            public void onJSONResponseError(JSONResponseException e) {
                // Assuming that the error is due to invalid user/password. The actual error is printed to Log. Fixes issue #40.
                Toast.makeText(
                        getBaseContext(),
                        "Username and/or password not valid.\nPlease check and try again.",
                        Toast.LENGTH_LONG).show();

                Log.d("Something went wrong", e.getErrorCode() + ": " + e.getErrorMessage());
            }
        };
    }
}
