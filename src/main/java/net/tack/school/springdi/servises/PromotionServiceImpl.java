package net.thumbtack.school.springdi.servises;

import net.thumbtack.school.springdi.model.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class PromotionServiceImpl implements PromotionService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionServiceImpl.class);
    private PublishingChannel[] publishingChannel;

    public PromotionServiceImpl(PublishingChannel... publishingChannel) {
        this.publishingChannel = publishingChannel;
    }

    @Override
    public void createCampaign(Recording recording, ZonedDateTime campaignCreateDate) {
        LOGGER.debug("Campaign is ceated");
    }
}
