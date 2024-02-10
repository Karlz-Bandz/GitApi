package controller;

import org.example.controller.GitController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class GitControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitController gitController;

    @Test
    void getRepositoriesTest_SUCCESS(){

    }
}
