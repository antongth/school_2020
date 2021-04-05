package net.thumbtack.school.springrest.servises;

import net.thumbtack.school.springrest.model.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class PromotionServiceImpl implements PromotionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Override
    public void createCampaign(Recording recording, ZonedDateTime campaignCreateDate) {
        LOGGER.debug("Campaign is ceated");
    }

    @Override
    public void stopCampaign(Recording recording) {
        LOGGER.debug("Campaign is stopped");
    }

    @Override
    public void deleteCampaign(Recording recording) {
        LOGGER.debug("Campaign is deleted");
    }

    @Override
    public String getCampaign(Recording recording) {
        LOGGER.debug("Campaign info is given");
        return "Campaign info on " + recording.toString();
    }

    @Override
    public Recording[] getAllCampaign() {
        return null;
    }
}
