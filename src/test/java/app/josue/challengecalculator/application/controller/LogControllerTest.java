package app.josue.challengecalculator.application.controller;

import app.josue.challengecalculator.application.controller.LogController;
import app.josue.challengecalculator.application.dto.response.AddResponseDto;
import app.josue.challengecalculator.domain.model.LogModel;
import app.josue.challengecalculator.domain.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = LogController.class)
public class LogControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LogService logService;

    private final String PATH_LOG = "/log";

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenLogOkThenOk() {
        var pageIndex = 0;
        var pageSize = 10;

        setUpMock();

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(PATH_LOG)
                        .queryParam("page", pageIndex)
                        .queryParam("size", pageSize)
                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").exists()
                .jsonPath("$.content").isArray()
                .jsonPath("$.totalPages").exists()
                .jsonPath("$.totalPages").isNumber()
                .jsonPath("$.totalElements").exists()
                .jsonPath("$.totalElements").isNumber()
                ;

        verify(logService, times(1)).getAll(any());
    }

    @Test
    void whenLogMissingThenOk() {

        setUpMock();

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(PATH_LOG)
                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").exists()
                .jsonPath("$.content").isArray()
                .jsonPath("$.totalPages").exists()
                .jsonPath("$.totalPages").isNumber()
                .jsonPath("$.totalElements").exists()
                .jsonPath("$.totalElements").isNumber()
        ;

        verify(logService, times(1)).getAll(any());
    }

    private void setUpMock() {
        var list = List.<LogModel>of();
        var result = new PageImpl<>(list);

        when(logService.getAll(any(Pageable.class)))
                .thenReturn(Mono.just(result));
    }

}
