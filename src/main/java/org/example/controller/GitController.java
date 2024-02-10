package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/git")
public class GitController {

    @Value("${github.api.url}")
    private String githubApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/limit")
    public ResponseEntity<Object> getLimit(){
        String apiUrl = githubApiUrl + "/rate_limit";

        ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);

        return ResponseEntity.ok(response.getBody());
    }
}
