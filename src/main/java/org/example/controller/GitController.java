package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/git")
public interface GitController {

    @GetMapping("/limit")
    ResponseEntity<Object> getLimit();

    @GetMapping("/repositories/{username}")
    ResponseEntity<Object> getRepositories(@PathVariable("username") String username);

    @GetMapping("/commit/{username}/{repoName}/{branchName}")
    ResponseEntity<String> getLastCommitSha(@PathVariable("username") String userName,
                            @PathVariable("repoName")String repoName,
                            @PathVariable("branchName")String branchName);

    @GetMapping("/branch/{username}/{repoName}")
    ResponseEntity<Map<String, String>> getBranchesForRepository(@PathVariable("username") String userName,
                                                 @PathVariable("repoName")String repoName);

}