package net.thumbtack.school.springdi.servises;

import net.thumbtack.school.springdi.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

public interface PublishingChannel {

    void publish(Recording recording, ZonedDateTime publishAvailableDate);
}
