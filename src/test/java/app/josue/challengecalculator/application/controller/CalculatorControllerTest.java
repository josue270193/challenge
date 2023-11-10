package app.josue.challengecalculator.application.controller;

import app.josue.challengecalculator.application.controller.CalculatorController;
import app.josue.challengecalculator.application.dto.request.AddRequestDto;
import app.josue.challengecalculator.application.dto.response.AddResponseDto;
import app.josue.challengecalculator.application.service.CalculatorService;
import app.josue.challengecalculator.domain.model.LogModel;
import app.josue.challengecalculator.domain.service.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CalculatorService calculatorService;

    @MockBean
    private LogService logService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    private final String PATH_ADD = "/calculator/add";

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenAddOkThenOk() {
        var request = new AddRequestDto(
                new Random().nextInt(1, 10),
                new Random().nextInt(1, 10)
        );

        setUpMock(request);

        webClient.post()
                .uri(PATH_ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").exists()
                .jsonPath("$.result").isNumber();

        verify(calculatorService, times(1)).add(any());
        verify(logService, times(1)).save(any());
    }

    @Test
    void whenAddMissingThenError() {
        var request = new AddRequestDto(
                new Random().nextInt(1, 10),
                null
        );

        webClient.post()
                .uri(PATH_ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .is4xxClientError();

        verify(calculatorService, times(0)).add(any());
        verify(logService, times(0)).save(any());
    }

    private void setUpMock(AddRequestDto request) {

        var response = new AddResponseDto(BigDecimal.valueOf(request.value1() + request.value2() + 1D));

        when(calculatorService.add(any(AddRequestDto.class)))
                .thenReturn(Mono.just(response));
        when(logService.save(any(LogModel.class)))
                .thenReturn(null);
    }

}
