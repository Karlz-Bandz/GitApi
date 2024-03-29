package org.example.controller.impl;

import org.example.controller.GitController;
import org.example.dto.GitMasterDto;
import org.example.dto.RateLimitDto;
import org.example.service.GitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitControllerImpl implements GitController {

    private final GitService gitService;

    public GitControllerImpl(GitService gitService) {
        this.gitService = gitService;
    }

    @Override
    public ResponseEntity<RateLimitDto> getLimit() {
        return ResponseEntity.ok(gitService.getLimit());
    }

    @Override
    public ResponseEntity<GitMasterDto> getRepositories(String username) {
        return ResponseEntity.ok(gitService.getRepositories(username));
    }
}