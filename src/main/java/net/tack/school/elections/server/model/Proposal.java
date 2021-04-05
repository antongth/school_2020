package net.thumbtack.school.elections.server.model;


public class Proposal {
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private String text;
    private String author;
    private float averageRating;
    private final int id;

    public Proposal(String text, String voter) {
        this.text = text;
        this.author = voter;
        this.id = IdGenerator.getNextId();
        this.averageRating = MAX_RATING;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getAuthor() {
        return author;
    }

    public void setCityAuthor() {
        this.author = "City N";
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }
}
