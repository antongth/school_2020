package net.thumbtack.school.springrest.model;

public class AudioRec extends Recording{
    private TrackType type = TrackType.AUDIO;

    public AudioRec(Recording recording) {
        super.artist = recording.getArtist();
        super.name = recording.getName();
        super.album = recording.getAlbum();
        super.year = recording.getYear();
        super.pictureAlbumLink = recording.getPictureAlbumLink();
        super.genre = recording.getGenre();
        super.duration = recording.getDuration();
        super.fileLink = recording.getFileLink();
    }

    public TrackType getType() {
        return type;
    }
}
