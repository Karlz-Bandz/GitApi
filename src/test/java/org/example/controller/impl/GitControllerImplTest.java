package org.example.controller.impl;

import org.example.service.GitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitControllerImplTest {

    @Mock
    private GitService gitService;

    @InjectMocks
    private GitControllerImpl gitController;

    @Test
    void getLimitControllerTest(){
        ResponseEntity<Object> mockResponse = ResponseEntity.ok("mockResponse");

        when(gitService.getLimit()).thenReturn(mockResponse);

        ResponseEntity<Object> response = gitController.getLimit();

        assertEquals(response, mockResponse);
    }
}
