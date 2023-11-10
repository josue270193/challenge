package app.josue.challengecalculator.infrastructure.outbound;

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
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PercentageClientServiceTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private PercentageClientService service = new PercentageClientServiceImpl();

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetPercentageOkThenOk() {
        setUpMock();

        service.setBoundMin(1L);
        service.setBoundMax(2L);

        var result = service.getPercentage();
        StepVerifier.create(result)
                .expectNextMatches(item -> item.percentage() != null)
                .verifyComplete();
        verify(cacheManager, times(1)).getCache(anyString());
        verify(cache, times(0)).get(anyString());
        verify(cache, times(1)).put(anyString(), any());
    }

    @Test
    void whenGetPercentageTimeoutThenOk() {
        setUpMock();

        service.setBoundMin(3L);
        service.setBoundMax(4L);

        var result = service.getPercentage();
        StepVerifier.create(result)
                .expectNextMatches(item -> item.percentage() != null)
                .verifyComplete();
        verify(cacheManager, times(1)).getCache(anyString());
        verify(cache, times(1)).get(anyString(), any(Class.class));
        verify(cache, times(0)).put(anyString(), any());
    }

    private void setUpMock() {
        var percentage = new PercentageModel(10D);
        when(cache.get(anyString(), any(Class.class))).thenReturn(percentage);
        doNothing().when(cache).put(anyString(), any());
        when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

}
