package org.example.service.impl;

import org.example.dto.GitMasterDto;
import org.example.dto.GitDto;
import org.example.dto.BranchDto;
import org.example.dto.RepoDto;
import org.example.exception.git.GitNotFoundException;
import org.example.service.GitService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GitServiceImpl implements GitService {

    private static final String GIT_API_URL = "https://api.github.com";

    private final RestTemplate restTemplate;

    public GitServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Object> getLimit() {
        String apiUrl = GIT_API_URL + "/rate_limit";

        ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);

        return ResponseEntity.ok(response.getBody());
    }

    @Override
    public ResponseEntity<GitMasterDto> getRepositories(String username) {
        String apiUrl = GIT_API_URL + "/users/" + username + "/repos";

        try {
            ResponseEntity<RepoDto[]> response = restTemplate.getForEntity(apiUrl, RepoDto[].class);
            RepoDto[] repositories = response.getBody();

            if(repositories != null && repositories.length > 0){
                GitDto[] responses = new GitDto[repositories.length];
                for (int i = 0;  i < repositories.length; i++) {
                    String repoName = repositories[i].getName();
                    BranchDto[] branches = getBranchForRepository(username, repoName)
                            .getBody();
                    GitDto gitDto = GitDto.builder()
                            .repoName(repoName)
                            .branches(branches)
                            .build();
                    responses[i] = gitDto;
                }

                GitMasterDto userData = GitMasterDto.builder()
                        .userName(username)
                        .repositories(responses)
                        .build();

                return ResponseEntity.ok(userData);
            }else{
                throw new GitNotFoundException(username + " doesn't have any repos");
            }
        }catch (HttpClientErrorException.NotFound notFound){
            throw new GitNotFoundException("Git user not found!");
        }
    }
    @Override
    public ResponseEntity<BranchDto[]> getBranchForRepository(String userName, String repoName) {
        String apiBranchUrl = GIT_API_URL + "/repos/" + userName + "/" + repoName + "/branches";
        return restTemplate.getForEntity(apiBranchUrl, BranchDto[].class);
    }
}
