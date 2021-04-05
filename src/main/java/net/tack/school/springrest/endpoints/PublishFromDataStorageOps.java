package net.thumbtack.school.springrest.endpoints;

import net.thumbtack.school.springrest.dto.Response;
import net.thumbtack.school.springrest.model.AudioRec;
import net.thumbtack.school.springrest.model.Recording;
import net.thumbtack.school.springrest.model.VideoRec;
import net.thumbtack.school.springrest.servises.PromotionService;
import net.thumbtack.school.springrest.servises.PublishingChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/channels")
public class PublishFromDataStorageOps {
    private PublishingChannel yandexMusicChannel;
    private PublishingChannel youtubeMusicChannel;
    private PublishingChannel itunesChannel;
    private List<String> idsOfRecordings;

    public PublishFromDataStorageOps(@Qualifier("serviceYandex") PublishingChannel yandexMusicChannel,
                                     @Qualifier("serviceYouTube") PublishingChannel youtubeMusicChannel,
                                     @Qualifier("serviceItunes") PublishingChannel itunesChannel) {
        this.yandexMusicChannel = yandexMusicChannel;
        this.youtubeMusicChannel = youtubeMusicChannel;
        this.itunesChannel = itunesChannel;
        idsOfRecordings = new ArrayList<>();
    }

    @PutMapping(value = "/yandex/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response publishToYanChannel(@PathVariable("id") String id,
                                        @RequestBody @Valid Recording recording) {
        idsOfRecordings.add(id + "yan");
        yandexMusicChannel.publish(recording, ZonedDateTime.now());
        return new Response("Done put " + id);
    }

    @PutMapping(value = "/youtube/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response publishToYouChannel(@PathVariable("id") String id,
                                        @RequestBody @Valid Recording recording) {
        idsOfRecordings.add(id + "you");
        youtubeMusicChannel.publish(recording, ZonedDateTime.now());
        return new Response("Done put " + id);
    }

    @PutMapping(value = "/itunes/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response publishToItChannel(@PathVariable("id") String id,
                                       @RequestBody @Valid Recording recording) {
        idsOfRecordings.add(id + "it");
        itunesChannel.publish(recording, ZonedDateTime.now());
        return new Response("Done put " + id);
    }

    @DeleteMapping(value = "/yandex/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteFromYanCannel(@PathVariable("id") String id,
                                        @RequestBody @Valid Recording recording) {
        if (idsOfRecordings.contains(id + "yan")) {
            yandexMusicChannel.delete(recording);
            return new Response("Done delete " + id);
        } else throw new IllegalStateException("Does not exist " + id);
    }

    @DeleteMapping(value = "/youtube/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteFromYouCannel(@PathVariable("id") String id,
                                        @RequestBody @Valid Recording recording) {
        if (idsOfRecordings.contains(id + "you")) {
            youtubeMusicChannel.delete(recording);
            return new Response("Done delete " + id);
        } else throw new IllegalStateException("Does not exist " + id);
    }

    @DeleteMapping(value = "/itunes/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteFromItYanCannel(@PathVariable("id") String id,
                                          @RequestBody @Valid Recording recording) {
        if (idsOfRecordings.contains(id + "it")) {
            itunesChannel.delete(recording);
            return new Response("Done delete " + id);
        } else throw new IllegalStateException("Does not exist " + id);
    }

}
