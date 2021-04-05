package net.thumbtack.school.springdi.model;

public class Recording {
    private String artist;
    //private TrackType type;
    private String name;
    private String album;
    private String year;
    private String pictureAlbumLink;
    private String genre;
    private String duration;
    private String fileLink;

    public Recording(String name, String fileLink) {
        //this.type = type;
        this.name = name;
        this.fileLink = fileLink;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPictureAlbumLink() {
        return pictureAlbumLink;
    }

    public void setPictureAlbumLink(String pictureAlbumLink) {
        this.pictureAlbumLink = pictureAlbumLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
