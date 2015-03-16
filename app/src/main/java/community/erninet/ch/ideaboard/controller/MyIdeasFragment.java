package community.erninet.ch.ideaboard.controller;

import android.app.AlertDialog;
import android.app.Application;
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
import community.erninet.ch.ideaboard.adapter.IdeaAdapter;
import community.erninet.ch.ideaboard.adapter.IdeasMockarooService;
import community.erninet.ch.ideaboard.application.Globals;
import community.erninet.ch.ideaboard.model.Idea;

/**
 * First fragment that provides some functionality (Loading mocked ideas and locally adding an idea
 */


public class MyIdeasFragment extends Fragment implements IdeaDialogFragment.EditIdeaDialogListener {

    //RecycleViewAdapter to update the list with ideas
    private IdeaAdapter adapterIdea = null;
    //Mock Service objcet
    private IdeasMockarooService ideaService = null;
    //Dialog fragment to create a new idea
    private IdeaDialogFragment dialog = null;

    public MyIdeasFragment() {
        // Required empty public constructor
    }

    public static MyIdeasFragment newInstance() {
        MyIdeasFragment fragment = new MyIdeasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Adapter with empty array of ideas
        ArrayList<Idea> ideaArray = new ArrayList<Idea>();
        // Create the adapter to convert the array to views
        adapterIdea = new IdeaAdapter(ideaArray);


        //initialize the mock service (needs to know the application context)
        ideaService = new IdeasMockarooService(getActivity().getApplication());

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ideas, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Globals globals = (Globals) getActivity().getApplication();

        //Give the button to idea some functionality
        ImageView myImage1 = (ImageView) getActivity().findViewById(R.id.imageViewAddIdea);
        //Add on click handler
        myImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new dialog fragment
                dialog = new IdeaDialogFragment();
                //Get a handle on the currently displayed fragment
                Fragment myFragment = getActivity().getSupportFragmentManager().findFragmentByTag("MY_IDEAS");
                //Set this fragment as listener for dialog results. the created idea will be passed back here
                dialog.setListener((IdeaDialogFragment.EditIdeaDialogListener) myFragment);
                //show the dialog
                dialog.show(getActivity().getFragmentManager(), "fragment_dialog");
            }
        });


        //ListView myList = (ListView) getActivity().findViewById(R.id.lvMyIdeas);
        //myList.setAdapter(adapterIdea);
        RecyclerView myView = (RecyclerView) getActivity().findViewById(R.id.rvMyIdeas);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myView.setLayoutManager(mLayoutManager);

        myView.setAdapter(adapterIdea);
        //the service also needs to know about the adapter. it will be updated when we get results from the backend
        ideaService.setAdapter(adapterIdea);
        //call mock service. everything that follows is handled by the (mock)-service, retrofit and the adapter
        if(globals.isOnline()) {
            ideaService.getIdeas();
        } else {
            showNoConnectionDialog();
        }

    }

    /**
     * Callback when the dialog editing has finished
     *
     * @param newIdea
     */
    public void onFinishEditIdea(Idea newIdea) {
        Idea addUser = newIdea;
        //add the currently logged in user as user information from the globals
        //TODO: this is still hardcoded. after login, the real username has to be stored
        addUser.setAuthor(((Globals) getActivity().getApplication()).getUser());
        //add the newly created user
        //TODO: user real implementation. The current implementation only adds a new idea locally. Didn't want to mock a post-service :)
        ideaService.createIdea(addUser);
    }

    private void showNoConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("No Internet Connection");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.show();
    }

}
