package controller;

import org.example.controller.HealthController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HealthControllerTest {

    @InjectMocks
    private HealthController healthController;

    @Test
    void healthCheckTest(){
        String expectedResult = "Health!";

        String result = healthController.healthCheck();

        assertEquals(expectedResult, result);
    }
}
