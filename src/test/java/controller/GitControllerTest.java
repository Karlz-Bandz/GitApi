package controller;

import org.example.controller.impl.GitControllerImpl;
import org.example.dto.GitDto;
import org.example.dto.RepoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitControllerImpl gitController;

    @Test
    void getRepositoriesTest_SUCCESS(){
    }
}
