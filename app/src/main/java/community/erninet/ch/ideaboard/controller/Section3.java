package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.TwitterAdapterRecycle;
import community.erninet.ch.ideaboard.adapter.TwitterServiceRecycle;
import community.erninet.ch.ideaboard.model.ErniTweet;


public class Section3 extends Fragment {
    private TwitterServiceRecycle service;
    private TwitterAdapterRecycle adapterCharlie;

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
        adapterCharlie = new TwitterAdapterRecycle(tweetArray);
        // Attach the adapter to a ListView


        service = new TwitterServiceRecycle();
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
        RecyclerView myView = (RecyclerView) getActivity().findViewById(R.id.rvCharlie);

        ImageView myImage2 = (ImageView) getActivity().findViewById(R.id.imageView2);
        ImageView myImage1 = (ImageView) getActivity().findViewById(R.id.imageView1);

        myImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCharlie.clear();
            }
        });

        myImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.getTweets();
            }
        });

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myView.setLayoutManager(mLayoutManager);

        myView.setAdapter(adapterCharlie);

        service.getTweets();
    }
}
