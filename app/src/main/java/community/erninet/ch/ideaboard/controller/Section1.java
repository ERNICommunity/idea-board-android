package community.erninet.ch.ideaboard.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import community.erninet.ch.ideaboard.R;


public class Section1 extends Fragment {


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


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section1, container, false);


    }


}
