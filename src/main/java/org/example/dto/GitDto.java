package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GitDto {
    private String repoName;
    private Map<String, String> branches;
}
