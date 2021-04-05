package net.thumbtack.school.springrest.servises;

import net.thumbtack.school.springrest.model.Recording;

import java.time.ZonedDateTime;

public interface PublishingChannel {

    void publish(Recording recording, ZonedDateTime publishAvailableDate);

    void delete(Recording recording);
}
