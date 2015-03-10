package community.erninet.ch.ideaboard.model;

/**
 * Created by ue65403 on 2015-03-09.
 */
public class Idea {
    private String title;
    private String description;
    private String author;
    private String status;
    private double rating;

    public Idea() {
        title = "";
        description = "";
        author = "";
        status = "";
        rating = 0.0;
    }

    public Idea(String title, String description, String author, String status, double rating) {
        setTitle(title);
        setDescription(description);
        setAuthor(author);
        setStatus(status);
        setRating(rating);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
