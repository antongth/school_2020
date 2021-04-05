package net.thumbtack.school.springdi.servises;

import net.thumbtack.school.database.mybatis.utils.MyBatisUtils;
import net.thumbtack.school.springdi.model.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

//@Component
public class ItunesChannel implements PublishingChannel{
    private static final Logger LOGGER = LoggerFactory.getLogger(ItunesChannel.class);

    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        LOGGER.debug("publishing to Itunes");
    }
}
