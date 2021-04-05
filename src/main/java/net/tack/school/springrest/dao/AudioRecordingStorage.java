package net.thumbtack.school.springrest.dao;

import org.springframework.stereotype.Component;

@Component
public class AudioRecordingStorage implements DataStorage {

    @Override
    public String save(String path) {
        return "~/audio/" + path;
    }
}
