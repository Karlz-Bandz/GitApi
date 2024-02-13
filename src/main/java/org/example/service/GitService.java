package org.example.service;

import org.example.dto.BranchDto;
import org.example.dto.GitMasterDto;
import org.springframework.http.ResponseEntity;

public interface GitService {

    ResponseEntity<Object> getLimit();

    ResponseEntity<GitMasterDto> getRepositories(String username);

    ResponseEntity<BranchDto[]> getBranchForRepository(String userName, String repoName);
}
