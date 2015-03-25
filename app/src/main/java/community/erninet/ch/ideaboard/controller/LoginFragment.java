package community.erninet.ch.ideaboard.controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.User;


public class LoginFragment extends Fragment implements SignUpDialogFragment.SignUpDialogListener {

    public static final String TAG = "LoginFragment";
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
        lockNavigationDrawer();



    }

    /**
     * this method prevents the user from using the navigation drawer by sliding
     * or the toggle button
     * Note that these two things have to be handled separately.
     */
    private void lockNavigationDrawer() {
        // first, disable user sliding the drawer open
        // get a reference to the drawerlayout
        DrawerLayout drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        // then lock it closed. Note that the fragment_navigation_drawer (listview) needs the property android:layout_gravity="start" defined
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.START);

        // second, hide the arrow on the action bar (because we cannot navigate up)
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            // disable the button, remove the caret and remove the icon. Don't ask me why this takes 3 lines.
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        } else {
            Log.e(TAG, "ActionBar returned null.");
        }
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
