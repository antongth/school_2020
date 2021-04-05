package net.thumbtack.school.springdi;

import net.thumbtack.school.springdi.servises.RecordingDataHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VideoStorageOps implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(VideoStorageOps.class);
    private RecordingDataHub recordingDataHub;

    //@Autowired
    public VideoStorageOps(RecordingDataHub recordingDataHub) {
        this.recordingDataHub = recordingDataHub;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("Video saving to Storage");
        String path = recordingDataHub.saveVideo("salt.mp4");
        LOGGER.debug("Video {} is saved to Storage", path);

    }
}
