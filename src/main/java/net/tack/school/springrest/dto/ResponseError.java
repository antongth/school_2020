package net.thumbtack.school.springrest.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseError {
    private List<String> allErrors = new ArrayList<>();

    public ResponseError() {
    }

    public List<String> getAllErrors() {
        return allErrors;
    }

    public void setAllErrors(List<String> allErrors) {
        this.allErrors = allErrors;
    }
}
