package net.thumbtack.school.springdi.servises;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AudioRecordingStorage implements DataStorage{

    @Override
    public String save(String path) {
        return "/audio/" + path;
    }
}
