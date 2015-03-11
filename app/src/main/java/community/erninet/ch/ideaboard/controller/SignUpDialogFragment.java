package community.erninet.ch.ideaboard.controller;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import community.erninet.ch.ideaboard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpDialogFragment extends DialogFragment {
    private SignUpDialogListener mListener = null;

    public SignUpDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_dialog, container);
        getDialog().setTitle("Sign Up");
        return view;
    }

    public void onResume() {
        super.onResume();
    }

    public void setListener(SignUpDialogListener listener) {
        this.mListener = listener;
    }


    public interface SignUpDialogListener {
        void onSignUpDataEntered();
    }
}
