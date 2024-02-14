package org.example.service;

import org.example.dto.BranchDto;
import org.example.dto.GitMasterDto;

public interface GitService {

    Object getLimit();

    GitMasterDto getRepositories(String username);

    BranchDto[] getBranchForRepository(String userName, String repoName);
}
