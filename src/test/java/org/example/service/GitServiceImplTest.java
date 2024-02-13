package org.example.service;

import org.example.dto.GitMasterDto;
import org.example.dto.GitDto;
import org.example.dto.BranchDto;
import org.example.dto.CommitDto;
import org.example.dto.RepoDto;
import org.example.service.impl.GitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitServiceImpl gitService;

    private static final String GIT_API_URL = "https://api.github.com";

    @Test
    void getLimitTest(){
        String apiUrl = GIT_API_URL + "/rate_limit";

        when(restTemplate.getForEntity(apiUrl, Object.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Object> response = gitService.getLimit();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBranchesForRepositoryTest_SUCCESS(){
        String userName = "TestUser";
        String repoName = "TestRepo";

        String apiBranchUrl = GIT_API_URL + "/repos/" + userName + "/" + repoName + "/branches";

        CommitDto commitDto1 = CommitDto.builder()
                .sha("444444555")
                .build();
        CommitDto commitDto2 = CommitDto.builder()
                .sha("333334555")
                .build();

        BranchDto branch1 = BranchDto.builder()
                .name("Branch1")
                .commit(commitDto1)
                .build();
        BranchDto branch2 = BranchDto.builder()
                .name("Branch2")
                .commit(commitDto2)
                .build();

        BranchDto[] branches = {branch1, branch2};

        when(restTemplate.getForEntity(apiBranchUrl, BranchDto[].class)).thenReturn(ResponseEntity.ok(branches));

        ResponseEntity<BranchDto[]> response = gitService.getBranchForRepository(userName, repoName);

        assertEquals(branches, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void getRepositoriesTest_ERROR(){
        String username = "TestUser";
        String gitRepoApi = GIT_API_URL + "/users/" + username + "/repos";

        when(restTemplate.getForEntity(gitRepoApi, RepoDto[].class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        HttpClientErrorException expectedResponse = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        HttpClientErrorException exceptionResponse = assertThrows(HttpClientErrorException.class, () ->
                gitService.getRepositories(username));

        assertEquals(expectedResponse.getMessage(),
                exceptionResponse.getMessage());
    }

    @Test
    void getRepositoriesTest_SUCCESS(){
        String username = "TestUser";
        String gitRepoApi = GIT_API_URL + "/users/" + username + "/repos";

        RepoDto repoDto1 = RepoDto.builder()
                .name("Repo1")
                .build();
        RepoDto repoDto2 = RepoDto.builder()
                .name("Repo2")
                .build();

        RepoDto[] repos = {repoDto1, repoDto2};

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

        BranchDto branch1 = BranchDto.builder()
                .name("Branch1")
                .commit(commitDto1)
                .build();
        BranchDto branch2 = BranchDto.builder()
                .name("Branch2")
                .commit(commitDto2)
                .build();
        BranchDto branch3 = BranchDto.builder()
                .name("Branch3")
                .commit(commitDto3)
                .build();
        BranchDto branch4 = BranchDto.builder()
                .name("Branch4")
                .commit(commitDto4)
                .build();

        BranchDto[] branches1 = {branch1, branch2};
        BranchDto[] branches2 = {branch3, branch4};

        GitDto gitDto1 = GitDto.builder()
                .repoName(repoDto1.getName())
                .branches(branches1)
                .build();
        GitDto gitDto2 = GitDto.builder()
                .repoName(repoDto2.getName())
                .branches(branches2)
                .build();

        GitDto[] responses = {gitDto1, gitDto2};
        GitMasterDto expectedResponse = GitMasterDto.builder()
                        .userName(username)
                        .repositories(responses)
                        .build();

        when(restTemplate.getForEntity(gitRepoApi, RepoDto[].class))
                .thenReturn(ResponseEntity.ok(repos));

        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto1.getName() + "/branches", BranchDto[].class))
                .thenReturn(ResponseEntity.ok(branches1));
        when(restTemplate.getForEntity(GIT_API_URL + "/repos/" + username + "/" + repoDto2.getName() + "/branches", BranchDto[].class))
                .thenReturn(ResponseEntity.ok(branches2));

        ResponseEntity<GitMasterDto> response = gitService.getRepositories(username);

        assertEquals(expectedResponse, response.getBody());
    }
}
