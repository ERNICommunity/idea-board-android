package community.erninet.ch.ideaboard.controller;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.List;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.Idea;

/**
 * Created by ue65403 on 2015-03-09.
 */
public class IdeaDialogFragment extends DialogFragment implements TagCloudLinkView.OnTagDeleteListener {

    //listener. thats where the user entries will be sent back
    private EditIdeaDialogListener mListener = null;

    public IdeaDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_idea, container);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
                TagCloudLinkView myTags = (TagCloudLinkView) getView().findViewById(R.id.ideaTagsView);
                List<Tag> tags = myTags.getTags();
                String tagString = "";
                for (int i = 0; i < tags.size() - 1; i++) {
                    tagString = tagString + tags.get(i).getText() + " ";
                }
                if (tags.size() > 1) {
                    tagString = tagString + tags.get(tags.size() - 1).getText();
                }
                //create new idea object and pass it back to the listener
                if (mListener != null) {
                    mListener.onFinishEditIdea(new Idea(Double.toString(Math.random() * 10000000), title, desc, tagString, "Droid", "Draft", 4.5));
                }
                //close the dialog
                dismiss();
            }
        });

        ((TagCloudLinkView) getView().findViewById(R.id.ideaTagsView)).setOnTagDeleteListener(this);

        EditText editText = (EditText) getView().findViewById(R.id.ideaTagEditText);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                TagCloudLinkView myTags = (TagCloudLinkView) getView().findViewById(R.id.ideaTagsView);
                if ((event != null && ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        || (event.getKeyCode() == KeyEvent.KEYCODE_SPACE) || (event.getKeyCode() == KeyEvent.KEYCODE_SEMICOLON)) || (actionId == EditorInfo.IME_ACTION_DONE))) {
                    myTags.add(new Tag((myTags.getTags().size() + 1), v.getText().toString().trim().replace(";", "")));
                    v.setText("");
                    myTags.drawTags();
                }
                return false;
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

    public void onTagDeleted(Tag tag, int position) {
        Log.d("Tag", "Listener");
    }

    /**
     * Interface for listeners of the edited data
     */
    public interface EditIdeaDialogListener {
        void onFinishEditIdea(Idea newIdea);
    }
}
