package org.example.controller.impl;

import org.example.dto.BranchDto;
import org.example.dto.CommitDto;
import org.example.dto.GitDto;
import org.example.dto.RepoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitControllerImpl gitController;

    private static final String GIT_API_URL = "https://api.github.com";

    @Test
    void getLimitTest(){
        String apiUrl = GIT_API_URL + "/rate_limit";

        when(restTemplate.getForEntity(apiUrl, Object.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Object> response = gitController.getLimit();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getRepositoriesTest(){
        String username = "TestUser";
        String gitRepoApi = "https://api.github.com/users/" + username + "/repos";

        RepoDto repoDto1 = RepoDto.builder()
                .name("Repo1")
                .build();
        RepoDto repoDto2 = RepoDto.builder()
                .name("Repo2")
                .build();
        RepoDto[] repos = {repoDto1, repoDto2};

        BranchDto branch1 = BranchDto.builder()
                .name("Branch1")
                .build();
        BranchDto branch2 = BranchDto.builder()
                .name("Branch2")
                .build();
        BranchDto branch3 = BranchDto.builder()
                .name("Branch3")
                .build();
        BranchDto branch4 = BranchDto.builder()
                .name("Branch4")
                .build();
        BranchDto[] branchArr1 = {branch1, branch2};
        BranchDto[] branchArr2 = {branch3, branch4};

        CommitDto commitDto1 = CommitDto.builder()
                .sha("122222234")
                .build();
        CommitDto commitDto2 = CommitDto.builder()
                .sha("222288889")
                .build();
        CommitDto commitDto3 = CommitDto.builder()
                .sha("555552234")
                .build();
        CommitDto commitDto4 = CommitDto.builder()
                .sha("999988889")
                .build();

        Map<String, String> branches1 = Map.of(
                branch1.getName(), commitDto1.getSha(),
                branch2.getName(), commitDto2.getSha()
        );

        Map<String, String> branches2 = Map.of(
                branch3.getName(), commitDto3.getSha(),
                branch4.getName(), commitDto4.getSha()
        );

        GitDto gitDto1 = GitDto.builder()
                .repoName(repoDto1.getName())
                .branches(branches1)
                .build();
        GitDto gitDto2 = GitDto.builder()
                .repoName(repoDto2.getName())
                .branches(branches2)
                .build();
        List<GitDto> expectResponse = Arrays.asList(gitDto1, gitDto2);

        when(restTemplate.getForEntity(gitRepoApi, RepoDto[].class))
                .thenReturn(ResponseEntity.ok(repos));

        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto1.getName() + "/branches", BranchDto[].class))
                .thenReturn(ResponseEntity.ok(branchArr1));
        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto2.getName() + "/branches", BranchDto[].class))
                .thenReturn(ResponseEntity.ok(branchArr2));

        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto1.getName() + "/commits/" + branch1.getName(), CommitDto.class))
                .thenReturn(ResponseEntity.ok(commitDto1));
        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto1.getName() + "/commits/" + branch2.getName(), CommitDto.class))
                .thenReturn(ResponseEntity.ok(commitDto2));
        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto2.getName() + "/commits/" + branch3.getName(), CommitDto.class))
                .thenReturn(ResponseEntity.ok(commitDto3));
        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto2.getName() + "/commits/" + branch4.getName(), CommitDto.class))
                .thenReturn(ResponseEntity.ok(commitDto4));

        ResponseEntity<Object> response = gitController.getRepositories(username);

        assertEquals(expectResponse, response.getBody());
    }
}
