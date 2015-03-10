package community.erninet.ch.ideaboard.controller;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import community.erninet.ch.ideaboard.R;

/**
 * Created by ue65403 on 2015-03-09.
 */
public class IdeaDialogFragment extends DialogFragment {

    public IdeaDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_idea, container);
        getDialog().setTitle("Edit this form");
        return view;
    }

    public void onResume() {
        super.onResume();
        Button myButton = (Button) this.getView().findViewById(R.id.doneButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
