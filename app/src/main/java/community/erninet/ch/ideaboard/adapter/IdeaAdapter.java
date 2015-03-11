package community.erninet.ch.ideaboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.Idea;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {
    private ArrayList<Idea> ideas;
    private int layoutType = 0;

    // Provide a suitable constructor (depends on the kind of dataset)
    public IdeaAdapter(ArrayList<Idea> myDataset) {
        ideas = myDataset;
    }

    public void add(int position, Idea item) {
        ideas.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Idea item) {
        int position = ideas.indexOf(item);
        ideas.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        ideas.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(ArrayList<Idea> newIdeas) {
        Iterator<Idea> myIt = newIdeas.iterator();
        while (myIt.hasNext()) {
            ideas.add(myIt.next());
        }
        notifyItemRangeInserted(getItemCount(), newIdeas.size());
    }

    public void clear() {
        int size = getItemCount();
        ideas.clear();
        notifyItemRangeRemoved(0, size);
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
        holder.txtTitle.setText(ideas.get(position).getTitle());
        holder.txtDescription.setText(ideas.get(position).getDescription());
        holder.txtAuthor.setText(ideas.get(position).getAuthor());
        holder.txtTags.setText(ideas.get(position).getTags());
        holder.txtRating.setText(Double.toString(ideas.get(position).getRating()));
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
        public TextView txtTags;
        public TextView txtRating;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.ideaTitleText);
            txtDescription = (TextView) v.findViewById(R.id.ideaDescriptionText);
            txtAuthor = (TextView) v.findViewById(R.id.ideaAuthorText);
            txtTags = (TextView) v.findViewById(R.id.ideaTagsText);
            txtRating = (TextView) v.findViewById(R.id.ideaRatingText);
        }
    }

}
