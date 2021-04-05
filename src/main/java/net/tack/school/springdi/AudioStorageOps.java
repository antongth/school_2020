package net.thumbtack.school.springdi;

import net.thumbtack.school.springdi.servises.RecordingDataHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AudioStorageOps implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(AudioStorageOps.class);
    private RecordingDataHub recordingDataHub;

    //@Autowired
    public AudioStorageOps(RecordingDataHub recordingDataHub) {
        this.recordingDataHub = recordingDataHub;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("Audio saving to Storage");
        String path = recordingDataHub.saveAudio("salt.mp3");
        LOGGER.debug("Audio {} is saved to Storage", path);

    }
}
