package org.example.controller.impl;

import org.example.controller.GitController;
import org.example.dto.GitDto;
import org.example.service.GitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GitControllerImpl implements GitController {

    private final GitService gitService;

    public GitControllerImpl(GitService gitService) {
        this.gitService = gitService;
    }

    @Override
    public ResponseEntity<Object> getLimit(){
        return gitService.getLimit();
    }

    @Override
    public ResponseEntity<List<GitDto>> getRepositories(String username){
        return gitService.getRepositories(username);
    }
}
