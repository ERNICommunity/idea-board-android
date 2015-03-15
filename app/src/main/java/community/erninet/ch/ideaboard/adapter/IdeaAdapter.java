package community.erninet.ch.ideaboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.ArrayList;
import java.util.Iterator;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.Idea;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {
    private ArrayList<Idea> ideas;
    private int layoutType = 0;

    // constructor
    public IdeaAdapter(ArrayList<Idea> myDataset) {
        ideas = myDataset;
    }

    /**
     * Add an item at a specific postion
     *
     * @param position postion, zero based index
     * @param item     idea-object
     */
    public void add(int position, Idea item) {
        ideas.add(position, item);
        //needs to be called in order to update the connected list
        notifyItemInserted(position);
    }

    /**
     * remove an item
     * @param item idea to remove
     */
    public void remove(Idea item) {
        int position = ideas.indexOf(item);
        ideas.remove(position);
        notifyDataSetChanged();
        //notifyItemRemoved(position);
    }

    /**
     * Remove an item at a specific position, zero based
     * @param position
     */
    public void remove(int position) {
        ideas.remove(position);
        //notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    /**
     * Add an array of ideas to the liste
     * @param newIdeas ArrayList of Idea-objects
     */
    public void addAll(ArrayList<Idea> newIdeas) {
        //itearte through the ideas and add the to the list
        Iterator<Idea> myIt = newIdeas.iterator();
        while (myIt.hasNext()) {
            ideas.add(myIt.next());
        }
        //notify the adapter the a whole range of items has been inserted
        notifyDataSetChanged();
        //notifyItemRangeInserted(getItemCount(), newIdeas.size());
    }

    /**
     * Remove all items from the list
     */
    public void clear() {
        int size = getItemCount();
        ideas.clear();
        notifyDataSetChanged();
        //notifyItemRangeRemoved(0, size);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IdeaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idea, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d("ViewHoler", Integer.toString(position));
        holder.txtTitle.setText(ideas.get(position).getTitle());
        holder.txtDescription.setText(ideas.get(position).getDescription());
        holder.txtAuthor.setText(ideas.get(position).getAuthor());
        holder.txtStatus.setText(ideas.get(position).getStatus());
        holder.txtRating.setText(Double.toString(ideas.get(position).getRating()));

        while (holder.lvTags.getTags().size() > 0) {
            holder.lvTags.remove(holder.lvTags.getTags().size() - 1);
        }


        Log.d("ViewHolder", Integer.toString(holder.lvTags.getTags().size()));
        //holder.lvTags.drawTags();
        String tagString = ideas.get(position).getTags();
        Log.d("ViewHoler", tagString);
        String[] tags = tagString.split(" ");
        for (int i = 0; i < tags.length; i++) {
            holder.lvTags.add(new Tag((1000 * position + i), tags[i]));
        }
        holder.lvTags.drawTags();
        Log.d("ViewHolder", Integer.toString(holder.lvTags.getTags().size()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ideas.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtAuthor;
        public TextView txtStatus;
        public TextView txtRating;
        public TagCloudLinkView lvTags;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.ideaTitleText);
            txtDescription = (TextView) v.findViewById(R.id.ideaDescriptionText);
            txtAuthor = (TextView) v.findViewById(R.id.ideaAuthorText);
            txtStatus = (TextView) v.findViewById(R.id.ideaStatusText);
            txtRating = (TextView) v.findViewById(R.id.ideaRatingText);
            lvTags = (TagCloudLinkView) v.findViewById(R.id.ideaTagsCloud);
        }
    }

}
