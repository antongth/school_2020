package net.thumbtack.school.springrest.endpoints;

import net.thumbtack.school.springrest.dto.Response;
import net.thumbtack.school.springrest.model.Recording;
import net.thumbtack.school.springrest.servises.PromotionService;
import net.thumbtack.school.springrest.servises.PromotionServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/promo/campaigns")
public class PromotionOps {
    private PromotionServiceImpl promotionService;
    private List<String> idOfCampaigns;

    public PromotionOps(PromotionServiceImpl promotionService) {
        this.promotionService = promotionService;
        idOfCampaigns = new ArrayList<>();
    }

    @PostMapping(value = "/{idOfCampaign}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response createPromotionCampaign(@RequestParam(value = "to", required = false, defaultValue = "youtube") String to,
                                            @PathVariable("idOfCampaign") String idOfCampaign,
                                            @RequestBody @Valid Recording recording) {
        idOfCampaigns.add(idOfCampaign);
        switch (to) {
            case "youtube": promotionService.createCampaign(recording, ZonedDateTime.now()); break;
            case "yandex" : promotionService.createCampaign(recording, ZonedDateTime.now()); break;
            case "itunes" : promotionService.createCampaign(recording, ZonedDateTime.now()); break;
            default:
                throw new IllegalStateException("Unexpected value: " + to);
        }
        return new Response("Done post " + to);
    }

    @PatchMapping(value = "/{idOfCampaign}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response stopPromotionCampaign(@RequestParam(value = "to", required = false, defaultValue = "youtube") String to,
                                          @PathVariable("idOfCampaign") String idOfCampaign,
                                          @RequestBody @Valid Recording recording) {
        if (idOfCampaigns.contains(idOfCampaign)) {
            switch (to) {
                case "youtube": promotionService.stopCampaign(recording); break;
                case "yandex" : promotionService.stopCampaign(recording); break;
                case "itunes" : promotionService.stopCampaign(recording); break;
                case "all" : {promotionService.stopCampaign(recording); break;}
                default:
                    throw new IllegalStateException("Unexpected value: " + to);
            }
            return new Response("Done put " + to);
        } else throw new IllegalStateException("Unexpected value: " + idOfCampaign);
    }

    @DeleteMapping(value = "/{idOfCampaign}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response deletPromotionCampaign(@RequestParam(value = "to", required = false, defaultValue = "youtube") String to,
                                           @PathVariable("idOfCampaign") String idOfCampaign,
                                           @RequestBody @Valid Recording recording) {
        if (idOfCampaigns.contains(idOfCampaign)) {
            switch (to) {
                case "youtube": promotionService.deleteCampaign(recording); break;
                case "yandex" : promotionService.deleteCampaign(recording); break;
                case "itunes" : promotionService.deleteCampaign(recording); break;
                default:
                    throw new IllegalStateException("Unexpected value: " + to);
            }
            return new Response("Done delete " + to);
        } else throw new IllegalStateException("Unexpected value: " + idOfCampaign);
    }

    @GetMapping(value = "/{idOfCampaign}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getPromotionCampaign(@PathVariable("idOfCampaign") String idOfCampaign,
                                         @RequestBody @Valid Recording recording) {
        if (idOfCampaigns.contains(idOfCampaign)) {
            return new Response(promotionService.getCampaign(recording));
        } else throw new IllegalStateException("Unexpected value: " + idOfCampaign);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Recording[] getAllPromotionsCampaign() {
        return promotionService.getAllCampaign();
    }
}
