package net.thumbtack.school.springrest.dto;

import net.thumbtack.school.springrest.validator.YearOfRelease;

import javax.validation.constraints.NotNull;

public class RecordingDto {
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

    public RecordingDto(@NotNull String artist,
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

    public RecordingDto() {

    }


}
