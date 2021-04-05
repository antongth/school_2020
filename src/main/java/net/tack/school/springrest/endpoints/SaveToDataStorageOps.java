package net.thumbtack.school.springrest.endpoints;

import net.thumbtack.school.springrest.dto.RecordingDto;
import net.thumbtack.school.springrest.model.AudioRec;
import net.thumbtack.school.springrest.model.Recording;
import net.thumbtack.school.springrest.model.VideoRec;
import net.thumbtack.school.springrest.servises.RecordingDataHub;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/storage")
public class SaveToDataStorageOps {
    private RecordingDataHub recordingDataHub;

    public SaveToDataStorageOps(RecordingDataHub recordingDataHub) {
        this.recordingDataHub = recordingDataHub;
    }

    @PostMapping(value = "/audios/{idOfAudioResource}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Recording saveAudioToStorage(@PathVariable("idOfAudioResource") String idOfAudioResource,
                                   @RequestBody @Valid RecordingDto recordingDto) {
        Recording recording = new Recording();
        AudioRec audioRec = new AudioRec(recording);
        audioRec.setRecIdInDataStorage(idOfAudioResource);
        audioRec.setFileLink(recordingDataHub.save(audioRec));
        return audioRec;

    }

    @PostMapping(value = "/videos/{idOfVideoResource}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Recording saveVideoToStorage(@PathVariable("idOfVideoResource") String idOfVideoResource,
                                   @RequestBody @Valid RecordingDto recordingDto) {
        Recording recording = new Recording();
        VideoRec videoRec = new VideoRec(recording);
        videoRec.setRecIdInDataStorage(idOfVideoResource);
        videoRec.setFileLink(recordingDataHub.save(videoRec));
        return videoRec;
    }
}