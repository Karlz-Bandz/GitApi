package org.example.dto;

import java.util.List;

public class GitDto {

    private String repoName;
    private List<String> branches;

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }

    public String getRepoName() {
        return repoName;
    }

    public List<String> getBranches() {
        return branches;
    }
}
