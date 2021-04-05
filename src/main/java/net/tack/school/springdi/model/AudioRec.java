package net.thumbtack.school.springdi.model;

public class AudioRec extends Recording{
    private TrackType type = TrackType.AUDIO;

    public AudioRec(String name, String fileLink) {
        super(name, fileLink);
    }

    public TrackType getType() {
        return type;
    }

    public void setType(TrackType type) {
        this.type = type;
    }
}
