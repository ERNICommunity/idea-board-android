package community.erninet.ch.ideaboard.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ue65403 on 2015-02-24.
 */
public class ErniTweet {
    @SerializedName("created_at")
    private String DateCreated;
    @SerializedName("id")
    private String Id;
    @SerializedName("text")
    private String Text;

    public ErniTweet(String Date, String id, String text) {
        this.DateCreated = Date;
        this.Id = id;
        this.Text = text;
    }

    public String getDateCreated() {
        return this.DateCreated;
    }

    public void setDateCreated(String String) {
        this.DateCreated = String;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getText() {
        return this.Text;
    }

    public void setText(String text) {
        this.Text = text;
    }
}
