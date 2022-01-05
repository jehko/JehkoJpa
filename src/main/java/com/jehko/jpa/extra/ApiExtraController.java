package com.jehko.jpa.extra;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
public class ApiExtraController {

    @GetMapping("/api/extra/test")
    public String extraTest() {

        try {
            URI uri = new URI("");
            RestTemplate restTemplate = new RestTemplate();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
