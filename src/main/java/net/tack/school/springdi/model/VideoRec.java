package net.thumbtack.school.springdi.model;

public class VideoRec extends Recording{
    private TrackType type = TrackType.VIDEO;

    public VideoRec(String name, String fileLink) {
        super(name, fileLink);
    }

    public TrackType getType() {
        return type;
    }

    public void setType(TrackType type) {
        this.type = type;
    }
}
