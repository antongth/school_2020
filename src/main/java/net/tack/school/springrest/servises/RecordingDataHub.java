package net.thumbtack.school.springrest.servises;

import net.thumbtack.school.springrest.dao.AudioRecordingStorage;
import net.thumbtack.school.springrest.dao.DataStorage;
import net.thumbtack.school.springrest.dao.VideoRecordingStorage;
import net.thumbtack.school.springrest.model.AudioRec;
import net.thumbtack.school.springrest.model.VideoRec;
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

    public String save(AudioRec recording) {
        LOGGER.debug("saving Audio {} to AudioStorage", recording);
        return audioRecordingStorage.save(recording.getFileLink());
    }

    public String save(VideoRec recording) {
        LOGGER.debug("saving Video {} to VideoStorage", recording);
        return videoRecordingStorage.save(recording.getFileLink());
    }
}
