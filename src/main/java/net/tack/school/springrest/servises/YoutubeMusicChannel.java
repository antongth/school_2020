package net.thumbtack.school.springrest.servises;

import net.thumbtack.school.springrest.model.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

//@Component
public class YoutubeMusicChannel implements PublishingChannel {
    private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeMusicChannel.class);

    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        LOGGER.debug("publishing to YoutubeMusic");

    }

    @Override
    public void delete(Recording recording) {
        LOGGER.debug("deleting from YoutubeMusic");
    }
}
