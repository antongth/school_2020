package net.thumbtack.school.springrest.servises;

import net.thumbtack.school.springrest.model.Recording;

import java.time.ZonedDateTime;

public interface PromotionService {

    void createCampaign(Recording recording, ZonedDateTime campaignCreateDate);

    void stopCampaign(Recording recording);

    void deleteCampaign(Recording recording);

    String getCampaign(Recording recording);

    Recording[] getAllCampaign();
}
