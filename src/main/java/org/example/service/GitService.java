package org.example.service;

import org.example.dto.GitMasterDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GitService {

    ResponseEntity<Object> getLimit();

    ResponseEntity<GitMasterDto> getRepositories(String username);

    ResponseEntity<String> getLastCommitSha(String userName, String repoName, String branchName);

    ResponseEntity<Map<String, String>> getBranchesForRepository(String userName, String repoName);
}
