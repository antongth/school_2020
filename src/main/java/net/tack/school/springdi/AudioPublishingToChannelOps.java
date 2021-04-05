package net.thumbtack.school.springdi;

import net.thumbtack.school.springdi.model.AudioRec;
import net.thumbtack.school.springdi.model.Recording;
import net.thumbtack.school.springdi.model.TrackType;
import net.thumbtack.school.springdi.servises.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class AudioPublishingToChannelOps implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(AudioPublishingToChannelOps.class);
    PublishingChannel yandexMusicChannel;
    PublishingChannel youtubeMusicChannel;
    PublishingChannel itunesChannel;

    //@Autowired
    public AudioPublishingToChannelOps(@Qualifier("serviceYandex") PublishingChannel yandexMusicChannel,
                                       @Qualifier("serviceYouTube") PublishingChannel youtubeMusicChannel,
                                       @Qualifier("serviceItunes") PublishingChannel itunesChannel) {
        this.yandexMusicChannel = yandexMusicChannel;
        this.youtubeMusicChannel = youtubeMusicChannel;
        this.itunesChannel = itunesChannel;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("publishing to proper platforms");
        Recording recordingAudio = new AudioRec("Salt", "/audio/salt.mp3");
        ZonedDateTime nowDateTime = ZonedDateTime.now();
        yandexMusicChannel.publish(recordingAudio, nowDateTime);
        youtubeMusicChannel.publish(recordingAudio, nowDateTime);
        itunesChannel.publish(recordingAudio, nowDateTime);
        LOGGER.debug("audio {} is published", recordingAudio);
        LOGGER.debug("promotion preparing");
        PromotionService promotionService = new PromotionServiceImpl(yandexMusicChannel, youtubeMusicChannel, itunesChannel);
        promotionService.createCampaign(recordingAudio, nowDateTime.plusWeeks(1));
        LOGGER.debug("promotion is ready");

    }
}
