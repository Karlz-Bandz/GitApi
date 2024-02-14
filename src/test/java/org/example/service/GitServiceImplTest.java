package org.example.service;

import org.example.dto.GitMasterDto;
import org.example.dto.GitDto;
import org.example.dto.BranchDto;
import org.example.dto.CommitDto;
import org.example.dto.RepoDto;
import org.example.exception.git.GitNotFoundException;
import org.example.service.impl.GitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

        String mockResponse = "mock";

        when(restTemplate.getForObject(apiUrl, Object.class))
                .thenReturn(mockResponse);

        Object response = gitService.getLimit();

        assertEquals(mockResponse, response);
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

        when(restTemplate.getForObject(apiBranchUrl, BranchDto[].class)).thenReturn(branches);

        BranchDto[] response = gitService.getBranchForRepository(userName, repoName);

        assertEquals(branches, response);
    }

    @Test
    void getRepositoriesTest_USER_DOESNT_HAVE_REPOS_ERROR(){
        String username = "TestUser";
        String gitRepoApi = GIT_API_URL + "/users/" + username + "/repos";
        RepoDto[] mockRepo = {};

        when(restTemplate.getForObject(gitRepoApi, RepoDto[].class))
                .thenReturn(mockRepo);

        GitNotFoundException expectedResponse = new GitNotFoundException(username + " doesn't have any repos!");

        GitNotFoundException exceptionResponse = assertThrows(GitNotFoundException.class, () ->
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

        when(restTemplate.getForObject(gitRepoApi, RepoDto[].class))
                .thenReturn(repos);

        when(restTemplate.getForObject(GIT_API_URL + "/repos/" + username + "/" + repoDto1.getName() + "/branches", BranchDto[].class))
                .thenReturn(branches1);
        when(restTemplate.getForObject(GIT_API_URL + "/repos/" + username + "/" + repoDto2.getName() + "/branches", BranchDto[].class))
                .thenReturn(branches2);

        GitMasterDto response = gitService.getRepositories(username);

        assertEquals(expectedResponse, response);
    }
}
