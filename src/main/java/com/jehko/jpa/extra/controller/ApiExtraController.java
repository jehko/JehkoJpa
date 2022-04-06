package com.jehko.jpa.extra.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiExtraController {

    @GetMapping("/api/extra/test")
    public String extraTest() {

        Object object = null;
        try {
            URI uri = new URI("");
            RestTemplate restTemplate = new RestTemplate();
            object = restTemplate.getForObject(uri, String.class);

            log.info(object.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}
