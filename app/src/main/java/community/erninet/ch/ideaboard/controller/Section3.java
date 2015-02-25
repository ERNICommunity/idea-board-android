package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.TwitterAdapter;
import community.erninet.ch.ideaboard.adapter.TwitterService;
import community.erninet.ch.ideaboard.model.ErniTweet;


public class Section3 extends Fragment {
    private TwitterService service;
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


        service = new TwitterService();
        service.setAdapter(adapterCharlie);
        service.setUsername("charliesheen");

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

        service.getTweets();
    }
}
