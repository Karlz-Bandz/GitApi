package org.example.dto;

import java.util.Map;

public class GitDto {

    private String repoName;
    private Map<String, String> branches;

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setBranches(Map<String, String> branches) {
        this.branches = branches;
    }

    public String getRepoName() {
        return repoName;
    }

    public Map<String, String> getBranches() {
        return branches;
    }
}
