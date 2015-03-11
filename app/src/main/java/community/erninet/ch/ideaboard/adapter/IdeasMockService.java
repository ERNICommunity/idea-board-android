package community.erninet.ch.ideaboard.adapter;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.model.Idea;

/**
 * Created by ue65403 on 2015-03-09.
 */
public class IdeasMockService {

    private ArrayList<Idea> ideas = null;

    public IdeasMockService() {
        createIdeas();
    }

    public ArrayList<Idea> getIdeas() {
        return ideas;
    }

    public void createIdea(Idea newIdea) {
        ideas.add(newIdea);
    }

    private void createIdeas() {
        Idea idea1 = new Idea("Event 1", "Skiing", "Richard", "T1,T2", "Implemented", 5.0);
        Idea idea2 = new Idea("Tool 1", "Xamarin", "Richard", "T1,T2", "Dismissed", 0.2);
        this.ideas = new ArrayList<Idea>();
        this.ideas.add(idea1);
        this.ideas.add(idea2);
    }
}
