package net.thumbtack.school.springrest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.springrest.dto.ResponseError;
import net.thumbtack.school.springrest.errorhandler.ErrorHandler;
import net.thumbtack.school.springrest.model.Recording;
import net.thumbtack.school.springrest.servises.PromotionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PromotionOpsTest.class)
class PromotionOpsTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PromotionServiceImpl promotionService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getPromotionCampaign() throws Exception {
        Recording recording = new Recording("NoBodyOne", "One", 2008, "Rock", "8:02", "One.mp3");

//        mvc.perform(put("/promo/campaign/2").param("idOfCampaign","2")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(mapper.writeValueAsString(recording)));

        ResultActions result = mvc.perform(get("/promo/campaigns"));

        result.andExpect(content().string(""));
        //assertEquals(200, result.andReturn().getResponse().getStatus());
        ResponseError error = mapper.readValue(result.andReturn().getResponse().getContentAsString(), ResponseError.class);
        assertEquals(1, error.getAllErrors().size());
    }
}