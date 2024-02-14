package org.example.service;

import org.example.dto.BranchDto;
import org.example.dto.GitMasterDto;
import org.example.dto.RateLimitDto;

public interface GitService {

    RateLimitDto getLimit();

    GitMasterDto getRepositories(String username);

    BranchDto[] getBranchForRepository(String userName, String repoName);
}
