package org.example.controller.impl;

import org.example.dto.GitMasterDto;
import org.example.dto.RateLimitDto;
import org.example.dto.CommitDto;
import org.example.dto.RepoDto;
import org.example.dto.RateDto;
import org.example.dto.GitDto;
import org.example.dto.BranchDto;
import org.example.service.GitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitControllerImplTest {

    @Mock
    private GitService gitService;

    @InjectMocks
    private GitControllerImpl gitController;

    @Test
    void getRepositoriesControllerTest() {
        RepoDto repoDto1 = RepoDto.builder()
                .name("Repo1")
                .build();
        CommitDto commitDto1 = CommitDto.builder()
                .sha("333222211")
                .build();
        CommitDto commitDto2 = CommitDto.builder()
                .sha("334444411")
                .build();
        BranchDto branchDto1 = BranchDto.builder()
                .name("Branch1")
                .commit(commitDto1)
                .build();
        BranchDto branchDto2 = BranchDto.builder()
                .name("Branch2")
                .commit(commitDto2)
                .build();
        BranchDto[] mockBranches = {branchDto1, branchDto2};

        GitDto gitDto1 = GitDto.builder()
                .repoName(repoDto1.getName())
                .branches(mockBranches)
                .build();

        GitDto[] mockGitDtoList = {gitDto1, gitDto1};

        GitMasterDto mockGitMasterDto = GitMasterDto.builder()
                .userName("testUser")
                .repositories(mockGitDtoList)
                .build();

        ResponseEntity<GitMasterDto> mockResponse = ResponseEntity.ok(mockGitMasterDto);

        when(gitService.getRepositories(repoDto1.getName()))
                .thenReturn(mockGitMasterDto);

        ResponseEntity<GitMasterDto> response = gitController.getRepositories(repoDto1.getName());

        assertEquals(response, mockResponse);
    }

    @Test
    void getLimitControllerTest() {
        RateDto rate = RateDto.builder()
                .limit(5000)
                .remaining(4569)
                .build();
        RateLimitDto rateLimit = RateLimitDto.builder()
                .rate(rate)
                .build();

        when(gitService.getLimit()).thenReturn(rateLimit);

        ResponseEntity<RateLimitDto> response = gitController.getLimit();

        assertEquals(response.getBody(), rateLimit);
    }
}
