package org.example.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GitDto {
    private String repoName;
    private Map<String, String> branches;
}
