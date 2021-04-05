package net.thumbtack.school.springrest.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.springrest.model.Recording;
import net.thumbtack.school.springrest.servises.RecordingDataHub;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SaveToDataStorageOps.class)
class SaveToDataStorageOpsTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RecordingDataHub recordingDataHub;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testPostAudio() throws Exception {
        Recording recording = new Recording("NoBodyOne", "One", 2008, "Rock", "8:02", "One.mp3");

        ResultActions result = mvc.perform(post("/storage/audios/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(recording)));

        result.andExpect(status().isOk());
        assertEquals(result.andReturn().getResponse().getStatus(), 200);

    }


}