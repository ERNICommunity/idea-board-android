package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.IdeaAdapter;
import community.erninet.ch.ideaboard.adapter.IdeasMockService;
import community.erninet.ch.ideaboard.model.Idea;


public class OverviewFragment extends Fragment {

    private IdeaAdapter adapterIdea = null;
    private IdeasMockService ideaService = null;
    private IdeaDialogFragment dialog = null;

    public OverviewFragment() {
        // Required empty public constructor
    }

    // using the factory method here to create the fragment is best practice
    // allows you to pass stuff to fragments using the setArguments method (commented out here)
    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();

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
        return inflater.inflate(R.layout.fragment_overview, container, false);


    }

    @Override
    public void onResume() {
        super.onResume();

        ImageView myImage1 = (ImageView) getActivity().findViewById(R.id.imageView1);
        Button myButton = (Button) getActivity().findViewById(R.id.doneButton);
        myImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new IdeaDialogFragment();
                dialog.show(getActivity().getFragmentManager(), "fragment_dialog");
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // use a linear layout manager
        RecyclerView myView = (RecyclerView) getActivity().findViewById(R.id.rvIdeasOverview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myView.setLayoutManager(mLayoutManager);

        myView.setAdapter(adapterIdea);

        adapterIdea.addAll(ideaService.getIdeas());
    }
}
