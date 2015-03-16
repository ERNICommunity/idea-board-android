package community.erninet.ch.ideaboard.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import community.erninet.ch.ideaboard.R;


public class LoginFragment extends Fragment implements View.OnClickListener {


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Handle a click on either the login button or the link to the sign-up form
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        // forward to sign-up page
        if (v.getId() == R.id.textView2) {
            //TODO sign-up form here
        } else {
            //auth

        }

    }
}
