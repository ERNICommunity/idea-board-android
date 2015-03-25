package community.erninet.ch.ideaboard.controller;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.adapter.FormValidator;
import community.erninet.ch.ideaboard.adapter.UserBackend;
import community.erninet.ch.ideaboard.model.JSONResponseException;
import community.erninet.ch.ideaboard.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "SignUpFragment";
    EditText user;
    EditText pwd;
    EditText email;
    EditText phone;
    Button submit;
    TextView clickHere;
    User newUser = null;
    private SignUpDialogListener mListener = null;
    //storage variable to handle the create-user request
    private UserBackend.OnConversionCompleted callHandlerPost;
    //error handler to handle errors from the request
    private UserBackend.OnJSONResponseError errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_dialog, container, false);

        // disable the navigation drawer for this fragment
        // get a reference to the navigation drawer
        DrawerLayout drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, drawerLayout);

        // handler that handles the event if a user is successfully created
        //the method yields the userid as a string
        callHandlerPost = new UserBackend.OnConversionCompleted<String>() {
            @Override
            public void onConversionCompleted(String result) {
                if (user != null && mListener != null) {
                    mListener.onSignUpDataEntered(newUser);
                    ((MainActivity) getActivity()).stopProgress();
                    dismiss();
                }

            }
        };

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getDialog().setTitle("Sign-up user");

        //event handler if something went wrong creating the user
        //try creating a user with name richard to see funtionality
        errorHandler = new UserBackend.OnJSONResponseError() {
            @Override
            public void onJSONResponseError(JSONResponseException e) {

                ((MainActivity) getActivity()).stopProgress();
                // show an error. This doesn't check at the moment what the error was, but this is generally due to the username already taken
                Toast.makeText(
                        getActivity().getBaseContext(), "Could not sign up",
                        Toast.LENGTH_LONG).show();

            }
        };

        user = (EditText) view.findViewById(R.id.signUpUserInput);
        pwd = (EditText) view.findViewById(R.id.signUpPasswordInput);
        email = (EditText) view.findViewById(R.id.signUpEmailInput);
        phone = (EditText) view.findViewById(R.id.signUpPhoneInput);
        clickHere = (TextView) view.findViewById(R.id.textView2);

        // attach on click listener to submit button
        submit = (Button) view.findViewById(R.id.signUpButton);
        submit.setClickable(true);
        submit.setOnClickListener(this);

        // attach on click listener to the clickHere button
        clickHere.setClickable(true);
        clickHere.setOnClickListener(this);

//Attach validators for eMail
        EditText email = (EditText) view.findViewById(R.id.signUpEmailInput);
//This listener fires, when user pressed a finished input button
        email.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (FormValidator.validateEmail((EditText) v)) {
//Add code to hide keyboard here
                        return true;
                    } else {
                        v.setError(getString(R.string.signUpEmailErrorMessage));
                        return false;
                    }
                }
                return false;
            }
        });
//This listener fires, when the input lost focus
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!FormValidator.validateEmail((EditText) v)) {
                        ((EditText) v).setError(getString(R.string.signUpEmailErrorMessage));
                    }
                }
            }
        });
//Attach validators for Username
        EditText user = (EditText) view.findViewById(R.id.signUpUserInput);
        user.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (FormValidator.validateUsername((EditText) v)) {
                        return true;
                    } else {
                        v.setError(getString(R.string.signUpUsernameErrorMessage));
                        return false;
                    }
                }
                return false;
            }
        });
        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!FormValidator.validateUsername((EditText) v)) {
                        ((EditText) v).setError(getString(R.string.signUpUsernameErrorMessage));
                    }
                }
            }
        });
//Attach validators for password
        EditText password = (EditText) view.findViewById(R.id.signUpPasswordInput);
        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (FormValidator.validatePassword((EditText) v)) {
                        return true;
                    } else {
                        v.setError(getString(R.string.signUpPasswordErrorMessage));
                        return false;
                    }
                }
                return false;
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!FormValidator.validatePassword((EditText) v)) {
                        ((EditText) v).setError(getString(R.string.signUpPasswordErrorMessage));
                    }
                }
            }
        });

        //Attach validators for phone number
        final EditText phone = (EditText) view.findViewById(R.id.signUpPhoneInput);
        phone.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(phone.getWindowToken(), 0);

                    if (FormValidator.validatePhone((EditText) v)) {
                        return true;
                    } else {
                        v.setError(getString(R.string.signUpPhoneErrorMessage));
                        return false;
                    }
                }
                return false;
            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!FormValidator.validatePassword((EditText) v)) {
                        ((EditText) v).setError(getString(R.string.signUpPhoneErrorMessage));
                    }
                }
            }
        });
        return view;
    }

    /**
     * *
     * Handle form submission
     *
     * @param view
     */
    public void onClick(View view) {
        boolean inputValid = true;

        // check source and if it was the login text that was clicked on then go to the special login page
        if (view.getId() == R.id.textView2) {
            dismiss();
        } else {
//ultimately check user inputs
            if (!FormValidator.validateUsername(user)) {
                user.setError(getString(R.string.signUpUsernameErrorMessage));
                inputValid = false;
            }
            if (!FormValidator.validatePassword(pwd)) {
                pwd.setError(getString(R.string.signUpPasswordErrorMessage));
                inputValid = false;
            }
            if (!FormValidator.validateEmail(email)) {
                email.setError(getString(R.string.signUpEmailErrorMessage));
                inputValid = false;
            }
            if (!FormValidator.validatePhone(phone)) {
                phone.setError(getString(R.string.signUpPhoneErrorMessage));
                inputValid = false;
            }
// if the user input is valid, sign him up and then go to the MyMoods screen
            if (inputValid) {
                Log.d(TAG, "Input valid. Signing up user...");

                //store the input data in a user object
                newUser = new User(user.getText().toString(), phone.getText().toString(), email.getText().toString(), pwd.getText().toString());

                // call to API handler to sign up user
                //create a UserBackend object
                UserBackend createUser = new UserBackend();
                //attach the event handlers for response handling and error handling
                createUser.setListener(callHandlerPost);
                createUser.setErrorListener(errorHandler);
                //start the background task to create a new user
                ((MainActivity) getActivity()).startProgress("Creating new user");
                createUser.createUser(newUser);


            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setListener(SignUpDialogListener listener) {
        this.mListener = listener;
    }


    public interface SignUpDialogListener {
        void onSignUpDataEntered(User user);
    }
}
