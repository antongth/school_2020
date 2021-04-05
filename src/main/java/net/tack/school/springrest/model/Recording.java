package net.thumbtack.school.springrest.model;

import net.thumbtack.school.springrest.validator.YearOfRelease;

import javax.validation.constraints.NotNull;

//{"artist": "Nobodyone", "name": "salt", "year": 2017, "genre": "rock", "duration": "11:05", "fileLink": "salt.mp3"}
public class Recording {
    @NotNull
    protected String artist;
    //private TrackType type;
    @NotNull
    protected String name;
    protected String album;
    @YearOfRelease
    protected int year;
    protected String pictureAlbumLink;
    @NotNull
    protected String genre;
    @NotNull
    protected String duration;
    @NotNull
    protected String fileLink;
    protected String recIdInDataStorage;

    public Recording(@NotNull String artist,
                    @NotNull String name,
                    int year,
                    @NotNull String genre,
                    @NotNull String duration,
                    @NotNull String fileLink) {
        this.artist = artist;
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.duration = duration;
        this.fileLink = fileLink;
    }

    public Recording() {

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

    public String getRecIdInDataStorage() {
        return recIdInDataStorage;
    }

    public void setRecIdInDataStorage(String recIdInDataStorage) {
        this.recIdInDataStorage = recIdInDataStorage;
    }
}
