package net.thumbtack.school.springrest;

import net.thumbtack.school.springrest.dao.AudioRecordingStorage;
import net.thumbtack.school.springrest.dao.DataStorage;
import net.thumbtack.school.springrest.dao.VideoRecordingStorage;
import net.thumbtack.school.springrest.servises.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.thumbtack.school.springrest")
public class Config {

    @Bean("serviceItunes")
    PublishingChannel publishingChannelItunes() {
        return new ItunesChannel();
    }

    @Bean("serviceYandex")
    PublishingChannel publishingChannelYandex() {
        return new YandexMusicChannel();
    }

    @Bean("serviceYouTube")
    PublishingChannel publishingChannelYouTube() {
        return new YoutubeMusicChannel();
    }

    @Bean("videoStorage")
    DataStorage videoRecordingStorage() {
        return new VideoRecordingStorage();
    }

    @Bean("audioStorage")
    DataStorage audioRecordingStorage() {
        return new AudioRecordingStorage();
    }
}
