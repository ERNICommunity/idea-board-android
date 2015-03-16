package community.erninet.ch.ideaboard.adapter;

/**
 * Not to be used if RecyclerView works without issues
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.ArrayList;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.Idea;

public class IdeaAdapterList extends ArrayAdapter<Idea> {
    public IdeaAdapterList(Context context, ArrayList<Idea> tweets) {
        super(context, R.layout.item_idea, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Idea idea = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_idea, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.ideaTitleText);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.ideaDescriptionText);
            viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.ideaAuthorText);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.ideaStatusText);
            viewHolder.txtRating = (TextView) convertView.findViewById(R.id.ideaRatingText);
            viewHolder.lvTags = (TagCloudLinkView) convertView.findViewById(R.id.ideaTagsCloud);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.txtTitle.setText(idea.getTitle());
        viewHolder.txtDescription.setText(idea.getDescription());
        viewHolder.txtAuthor.setText(idea.getAuthor());
        viewHolder.txtStatus.setText(idea.getStatus());
        viewHolder.txtRating.setText(Double.toString(idea.getRating()));
        //TODO somehow tags are messed up, randomly added to wrong elements

        while (viewHolder.lvTags.getTags().size() > 0) {
            viewHolder.lvTags.remove(viewHolder.lvTags.getTags().size() - 1);
        }


        Log.d("ViewHolder", Integer.toString(viewHolder.lvTags.getTags().size()));
        //holder.lvTags.drawTags();
        String tagString = idea.getTags();
        Log.d("ViewHoler", tagString);
        String[] tags = tagString.split(" ");
        for (int i = 0; i < tags.length; i++) {
            viewHolder.lvTags.add(new Tag((1000 * position + i), tags[i]));
        }
        viewHolder.lvTags.drawTags();
        Log.d("ViewHolder", Integer.toString(viewHolder.lvTags.getTags().size()));
        // Return the completed view to render on screen
        return convertView;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtAuthor;
        public TextView txtStatus;
        public TextView txtRating;
        public TagCloudLinkView lvTags;
    }
}