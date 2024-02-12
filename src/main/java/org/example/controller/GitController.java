package org.example.controller;

import org.example.dto.GitMasterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/git")
public interface GitController {

    @GetMapping("/limit")
    ResponseEntity<Object> getLimit();

    @GetMapping("/repositories/{username}")
    ResponseEntity<GitMasterDto> getRepositories(@PathVariable("username") String username);
}