package net.thumbtack.school.springdi.servises;

import org.springframework.stereotype.Component;

@Component
public class VideoRecordingStorage implements DataStorage{

    @Override
    public String save(String path) {
        return "/video/"+ path;
    }
}
