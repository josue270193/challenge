package app.josue.challengecalculator.application.service;


import app.josue.challengecalculator.application.dto.request.AddRequestDto;
import app.josue.challengecalculator.application.service.CalculatorService;
import app.josue.challengecalculator.application.service.impl.CalculatorServiceImpl;
import app.josue.challengecalculator.domain.model.PercentageModel;
import app.josue.challengecalculator.domain.service.PercentageClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Random;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CalculatorServiceTest {

    @Mock
    private PercentageClientService percentageClientService;

    @InjectMocks
    private CalculatorService service = new CalculatorServiceImpl();

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

        var result = service.add(request);
        StepVerifier.create(result)
                .expectNextMatches(item -> item.result() != null &&
                        item.result().compareTo(BigDecimal.valueOf((double) request.value1() + request.value2())) >= 0
                )
                .verifyComplete();
        verify(percentageClientService, times(1)).getPercentage();
    }

    @Test
    void whenAddErrorThenError() {
        var request = new AddRequestDto(
                new Random().nextInt(1, 10),
                new Random().nextInt(1, 10)
        );
        when(percentageClientService.getPercentage())
                .thenReturn(Mono.empty());

        var result = service.add(request);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(percentageClientService, times(1)).getPercentage();
    }


    private void setUpMock(AddRequestDto request) {
        var percentage = new PercentageModel(10D);
        when(percentageClientService.getPercentage())
                .thenReturn(Mono.just(percentage));
    }

}
