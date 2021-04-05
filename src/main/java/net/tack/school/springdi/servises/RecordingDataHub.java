package net.thumbtack.school.springdi.servises;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RecordingDataHub {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordingDataHub.class);
    private DataStorage audioRecordingStorage;
    private DataStorage videoRecordingStorage;

    //@Autowired
    public RecordingDataHub(@Qualifier("audioStorage") DataStorage audioRecordingStorage, @Qualifier("videoStorage") DataStorage videoRecordingStorage) {
        this.audioRecordingStorage = audioRecordingStorage;
        this.videoRecordingStorage = videoRecordingStorage;
    }

    public DataStorage getAudioRecordingStorage() {
        return audioRecordingStorage;
    }

    public void setAudioRecordingStorage(AudioRecordingStorage audioRecordingStorage) {
        this.audioRecordingStorage = audioRecordingStorage;
    }

    public DataStorage getVideoStorage() {
        return videoRecordingStorage;
    }

    public void setVideoStorage(VideoRecordingStorage videoStorage) {
        this.videoRecordingStorage = videoStorage;
    }

    public String saveAudio(String path) {
        LOGGER.debug("saving Audio {} to AudioStorage", path);
        return audioRecordingStorage.save(path);
    }

    public String saveVideo(String path) {
        LOGGER.debug("saving Video {} to VideoStorage", path);
        return videoRecordingStorage.save(path);
    }
}
