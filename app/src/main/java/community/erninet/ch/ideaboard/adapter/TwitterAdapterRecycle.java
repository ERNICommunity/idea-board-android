package community.erninet.ch.ideaboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import community.erninet.ch.ideaboard.R;
import community.erninet.ch.ideaboard.model.ErniTweet;

public class TwitterAdapterRecycle extends RecyclerView.Adapter<TwitterAdapterRecycle.ViewHolder> {
    private ArrayList<ErniTweet> tweets;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TwitterAdapterRecycle(ArrayList<ErniTweet> myDataset) {
        tweets = myDataset;
    }

    public void add(int position, ErniTweet item) {
        tweets.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(ErniTweet item) {
        int position = tweets.indexOf(item);
        tweets.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        tweets.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(ArrayList<ErniTweet> newTweets) {
        Iterator<ErniTweet> myIt = newTweets.iterator();
        while (myIt.hasNext()) {
            tweets.add(myIt.next());
        }
        notifyItemRangeInserted(getItemCount(), newTweets.size());
    }

    public void clear() {
        int size = getItemCount();
        tweets.clear();
        notifyItemRangeRemoved(0, size);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TwitterAdapterRecycle.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtDate.setText(tweets.get(position).getDateCreated());
        holder.txtText.setText(tweets.get(position).getText());
        holder.txtText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(R.color.primary_text_disabled_material_light);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtDate;
        public TextView txtText;

        public ViewHolder(View v) {
            super(v);
            txtDate = (TextView) v.findViewById(R.id.tweetDate);
            txtText = (TextView) v.findViewById(R.id.tweetText);
        }
    }

}
