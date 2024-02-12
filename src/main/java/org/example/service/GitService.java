package org.example.service;

import org.example.dto.GitDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GitService {

    ResponseEntity<Object> getLimit();

    ResponseEntity<List<GitDto>> getRepositories(String username);

    ResponseEntity<String> getLastCommitSha(String userName, String repoName, String branchName);

    ResponseEntity<Map<String, String>> getBranchesForRepository(String userName, String repoName);
}
