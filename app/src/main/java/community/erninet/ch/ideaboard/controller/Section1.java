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


public class Section1 extends Fragment {
    private TwitterService service;
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

        service = new TwitterService();
        service.setAdapter(adapterErni);
        service.setUsername("ERNI");
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

        service.getTweets();
    }
}
