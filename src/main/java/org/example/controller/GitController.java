package org.example.controller;

import org.example.dto.BranchDto;
import org.example.dto.CommitDto;
import org.example.dto.GitDto;
import org.example.dto.RepoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/git")
public class GitController {

    @Value("${github.api.url}")
    private String githubApiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public GitController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/limit")
    public ResponseEntity<Object> getLimit(){
        String apiUrl = githubApiUrl + "/rate_limit";

        ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);

        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<Object> getRepositories(@PathVariable(name = "username") String username){
        String apiUrl = githubApiUrl + "/users/" + username + "/repos";

        try {
            ResponseEntity<RepoDto[]> response = restTemplate.getForEntity(apiUrl, RepoDto[].class);
            RepoDto[] repositories = response.getBody();
            List<GitDto> responses = new ArrayList<>();

            if(repositories != null){
                for (RepoDto repository : repositories) {
                    String repoName = repository.getName();
                    Map<String, String> branches = getBranchesForRepository(username, repoName);
                    GitDto gitDto = GitDto.builder()
                            .repoName(repoName)
                            .branches(branches)
                            .build();
                    responses.add(gitDto);
                }
            }

            return ResponseEntity.ok(responses);
        }catch (HttpClientErrorException.NotFound notFound){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status",
                            HttpStatus.NOT_FOUND.value(),
                            "message",
                            "user not found"));
        }
    }

    private String getLastCommitSha(String userName, String repoName, String branchName){
        String apiLastCommitUrl = githubApiUrl + "/repos/" + userName + "/" + repoName + "/commits/" + branchName;

        ResponseEntity<CommitDto> response = restTemplate.getForEntity(apiLastCommitUrl, CommitDto.class);

        CommitDto commit = response.getBody();

        if(commit != null){
            return commit.getSha();
        }else{
            return null;
        }
    }

    private Map<String, String> getBranchesForRepository(String userName, String repoName){
        String apiBranchUrl = githubApiUrl + "/repos/" + userName + "/" + repoName + "/branches";

        ResponseEntity<BranchDto[]> response = restTemplate.getForEntity(apiBranchUrl, BranchDto[].class);

        BranchDto[] branches = response.getBody();

        Map<String, String> branchesList = new HashMap<>();

        if(branches != null){
            for(BranchDto branch: branches){
                String lastCommit = getLastCommitSha(userName, repoName, branch.getName());
                branchesList.put(branch.getName(), lastCommit);
            }
        }
        return branchesList;
    }
}
