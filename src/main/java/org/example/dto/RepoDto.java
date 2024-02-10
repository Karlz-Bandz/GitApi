package org.example.dto;

public class RepoDto {

    private String name;

    public RepoDto(String name) {
        this.name = name;
    }

    public RepoDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
