package org.example.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GitService {

    ResponseEntity<Object> getLimit();

    ResponseEntity<Object> getRepositories(String username);

    ResponseEntity<String> getLastCommitSha(String userName, String repoName, String branchName);

    ResponseEntity<Map<String, String>> getBranchesForRepository(String userName, String repoName);
}
