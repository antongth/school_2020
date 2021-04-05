package net.thumbtack.school.springdi;

import net.thumbtack.school.springdi.model.Recording;
import net.thumbtack.school.springdi.model.TrackType;
import net.thumbtack.school.springdi.model.VideoRec;
import net.thumbtack.school.springdi.servises.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class VideoPublishingToChannelOps implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(VideoPublishingToChannelOps.class);
    PublishingChannel youtubeMusicChannel;

    //@Autowired
    public VideoPublishingToChannelOps(@Qualifier("serviceYouTube") PublishingChannel youtubeMusicChannel) {
        this.youtubeMusicChannel = youtubeMusicChannel;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("publishing to proper platforms");
        Recording recordingVideo = new VideoRec("Salt", "/video/salt.mp4");
        ZonedDateTime nowDateTime = ZonedDateTime.now();
        youtubeMusicChannel.publish(recordingVideo, nowDateTime);
        LOGGER.debug("video {} is published", recordingVideo);
        LOGGER.debug("promotion preparing");
        PromotionService promotionService = new PromotionServiceImpl(youtubeMusicChannel);
        promotionService.createCampaign(recordingVideo, nowDateTime.plusWeeks(2));
        LOGGER.debug("promotion is ready");
    }
}