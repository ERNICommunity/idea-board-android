package community.erninet.ch.ideaboard.controller;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.Idea;

/**
 * Created by ue65403 on 2015-03-09.
 */
public class IdeaDialogFragment extends DialogFragment {

    //listener. thats where the user entries will be sent back
    private EditIdeaDialogListener mListener = null;

    public IdeaDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_idea, container);
        getDialog().setTitle("New idea");
        return view;
    }

    public void onResume() {
        super.onResume();
        //Add functionality to the saveDraftButton. Currently this stores an idea locally. For the real implementation,
        //both, the publish and save button have to provide the desired functionality
        Button myButton = (Button) this.getView().findViewById(R.id.saveIdeaButton);
        //attach listener
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //collect user inputs
                String title = ((EditText) getView().findViewById(R.id.ideaTitleTextEdit)).getText().toString();
                String desc = ((EditText) getView().findViewById(R.id.ideaDescriptionEditText)).getText().toString();
                String tags = ((EditText) getView().findViewById(R.id.ideaTagsEditText)).getText().toString();
                //create new idea object and pass it back to the listener
                if (mListener != null) {
                    mListener.onFinishEditIdea(new Idea(Double.toString(Math.random() * 10000000), title, desc, tags, "Droid", "Draft", 4.5));
                }
                //close the dialog
                dismiss();
            }
        });
    }

    /**
     * Attach a listener to this dialog fragment
     *
     * @param listener
     */
    public void setListener(EditIdeaDialogListener listener) {
        this.mListener = listener;
    }

    /**
     * Interface for listeners of the edited data
     */
    public interface EditIdeaDialogListener {
        void onFinishEditIdea(Idea newIdea);
    }
}
