package net.thumbtack.school.springdi;

import net.thumbtack.school.springdi.servises.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.thumbtack.school.springdi")
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
