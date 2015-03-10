package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.IdeaAdapter;
import community.erninet.ch.ideaboard.adapter.IdeasMockService;
import community.erninet.ch.ideaboard.model.Idea;


public class DiscussAndVoteFragment extends Fragment {

    private IdeaAdapter adapterIdea = null;
    private IdeasMockService ideaService = null;

    public DiscussAndVoteFragment() {
        // Required empty public constructor
    }

    public static DiscussAndVoteFragment newInstance() {
        DiscussAndVoteFragment fragment = new DiscussAndVoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct the data source
        ArrayList<Idea> ideaArray = new ArrayList<Idea>();
        // Create the adapter to convert the array to views
        adapterIdea = new IdeaAdapter(ideaArray);
        // Attach the adapter to a ListView

        ideaService = new IdeasMockService();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discuss_and_vote, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView myView = (RecyclerView) getActivity().findViewById(R.id.rvDiscussAndVote);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myView.setLayoutManager(mLayoutManager);

        myView.setAdapter(adapterIdea);

    }
}
