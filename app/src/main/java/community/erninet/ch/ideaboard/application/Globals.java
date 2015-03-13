package community.erninet.ch.ideaboard.application;

import android.app.Application;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.model.Idea;


/**
 * Created by gus on 28/02/15.
 */
public class Globals extends Application {

    private boolean userLoggedIn = false;
    private String user = "Dummy User";
    private ArrayList<Idea> allIdeas = null;

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
}

