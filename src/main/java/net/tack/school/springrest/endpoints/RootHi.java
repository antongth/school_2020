package net.thumbtack.school.springrest.endpoints;

import net.thumbtack.school.springrest.dto.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootHi {

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response hiRoot() {
        return new Response("Hi");
    }

}
