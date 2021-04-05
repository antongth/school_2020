package net.thumbtack.school.springdi.servises;

import net.thumbtack.school.springdi.model.Recording;

import java.time.ZonedDateTime;

public interface PromotionService {

    void createCampaign(Recording recording, ZonedDateTime campaignCreateDate);
}
