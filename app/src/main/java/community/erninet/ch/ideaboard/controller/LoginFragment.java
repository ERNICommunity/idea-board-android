package community.erninet.ch.ideaboard.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.User;


public class LoginFragment extends Fragment implements SignUpDialogFragment.SignUpDialogListener {

    private SignUpDialogFragment mSignUpDialog = null;


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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myPwd.getWindowToken(), 0);
                ((MainActivity) getActivity()).authorizeUser(myUser.getText().toString(), myPwd.getText().toString());
            }
        });
        TextView myTextView = (TextView) this.getView().findViewById(R.id.textView2);
        myTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Create new dialog fragment
                mSignUpDialog = new SignUpDialogFragment();
                //Get a handle on the currently displayed fragment
                Fragment myFragment = getActivity().getSupportFragmentManager().findFragmentByTag("LOGIN");
                //Set this fragment as listener for dialog results. the created idea will be passed back here
                mSignUpDialog.setListener((SignUpDialogFragment.SignUpDialogListener) myFragment);
                //show the dialog
                mSignUpDialog.show(getActivity().getFragmentManager(), "fragment_dialog");
            }
        });
    }

    public void onSignUpDataEntered(User user) {
        ((MainActivity) getActivity()).authorizeUser(user.getUsername(), user.getPassword());
    }

}
