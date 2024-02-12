package org.example.controller.impl;

import org.example.dto.*;
import org.example.service.GitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitControllerImplTest {

    @Mock
    private GitService gitService;

    @InjectMocks
    private GitControllerImpl gitController;

    @Test
    void getRepositoriesControllerTest(){
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
                .build();
        BranchDto branchDto2 = BranchDto.builder()
                .name("Branch2")
                .build();
        Map<String, String> mockBranches = Map.of(
                branchDto1.getName(), commitDto1.getSha(),
                branchDto2.getName(), commitDto2.getSha()
        );
        GitDto gitDto1 = GitDto.builder()
                .repoName(repoDto1.getName())
                .branches(mockBranches)
                .build();
        List<GitDto> mockGitDtoList = Arrays.asList(gitDto1, gitDto1);
        GitMasterDto mockGitMasterDto = GitMasterDto.builder()
                .userName("testUser")
                .repositories(mockGitDtoList)
                .build();

        ResponseEntity<GitMasterDto> mockResponse = ResponseEntity.ok(mockGitMasterDto);

        when(gitService.getRepositories(repoDto1.getName()))
                .thenReturn(ResponseEntity.ok(mockGitMasterDto));

        ResponseEntity<GitMasterDto> response = gitController.getRepositories(repoDto1.getName());

        assertEquals(response, mockResponse);
    }

    @Test
    void getLimitControllerTest(){
        ResponseEntity<Object> mockResponse = ResponseEntity.ok("mockResponse");

        when(gitService.getLimit()).thenReturn(mockResponse);

        ResponseEntity<Object> response = gitController.getLimit();

        assertEquals(response, mockResponse);
    }
}
