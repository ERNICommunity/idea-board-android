package community.erninet.ch.ideaboard.application;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.model.Idea;


/**
 * Created by gus on 28/02/15.
 */
public class Globals extends Application {

    private boolean userLoggedIn = false;
    private String user = "Dummy User";
    private ArrayList<Idea> allIdeas = null;
    private final ConnectivityManager connectivityManager;

    public Globals() {
        connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        allIdeas = new ArrayList<Idea>();
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public ArrayList<Idea> getAllIdeas() {
        return allIdeas;
    }

    public void setAllIdeas(ArrayList<Idea> allIdeas) {
        this.allIdeas = allIdeas;
    }

    public boolean isOnline() {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}

