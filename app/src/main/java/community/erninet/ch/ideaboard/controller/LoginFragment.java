package community.erninet.ch.ideaboard.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import community.erninet.ch.ideaboard.R;


public class LoginFragment extends Fragment {


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
        Button myButton = (Button) this.getView().findViewById(R.id.login_loginform);
        //attach listener
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText myUser = (EditText) getView().findViewById(R.id.username_loginform);
                EditText myPwd = (EditText) getView().findViewById(R.id.password_loginform);
                ((MainActivity) getActivity()).authorizeUser(myUser.getText().toString(), myPwd.getText().toString());
            }
        });
    }

}
