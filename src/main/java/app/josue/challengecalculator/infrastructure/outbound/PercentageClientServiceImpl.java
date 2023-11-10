package app.josue.challengecalculator.infrastructure.outbound;

import app.josue.challengecalculator.domain.exception.ServerErrorException;
import app.josue.challengecalculator.domain.model.PercentageModel;
import app.josue.challengecalculator.domain.service.PercentageClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
public class PercentageClientServiceImpl implements PercentageClientService {

    private static final String CACHE_SERVICE_PERCENTAGE = "cache_percentage";
    private static final String KEY_GET_PERCENTAGE = "getPercentage";
    private final Logger log = LoggerFactory.getLogger(PercentageClientServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    private Long boundMin = 1L;
    private Long boundMax = 10L;

    @Override
    public Mono<PercentageModel> getPercentage() {
        // We could use a MockServer like WireMock
        var timeout = Duration.ofSeconds(3);
        var retry = 3;
        var result = new PercentageModel(10D);
        return Mono.just(result)
                .delayUntil(model -> {
                    var delay = getDelay();
                    log.info("Delay: {}", delay);
                    return Mono.just(model)
                            .delayElement(delay)
                            .onErrorResume(throwable -> Mono.just(model));
                })
                .timeout(timeout)
                .retryWhen(
                    Retry.fixedDelay(retry, Duration.ofMillis(100))
                            .doBeforeRetry(retrySignal -> log.info("Retry service: {}", retrySignal.totalRetries()))
                )
                .doOnSuccess(model -> {
                    log.info("Save on cache");
                    saveOnCache(KEY_GET_PERCENTAGE, model);
                })
                .onErrorResume(throwable -> {
                    log.error("Error after retry", throwable);
                    var model = getOnCache(KEY_GET_PERCENTAGE);
                    if (model != null) {
                        log.info("Using cache info: {}", model);
                        return Mono.just(model);
                    }
                    return Mono.error(new ServerErrorException("Error obtaining percentage"));
                });
    }

    private void saveOnCache(String key, PercentageModel model) {
        var cache = cacheManager.getCache(CACHE_SERVICE_PERCENTAGE);
        if (cache != null) {
            cache.put(key, model);
        }
    }

    private PercentageModel getOnCache(String key) {
        var cache = cacheManager.getCache(CACHE_SERVICE_PERCENTAGE);
        if (cache != null) {
            return cache.get(key, PercentageModel.class);
        }
        return null;
    }

    private Duration getDelay() {
        return Duration.of(new Random().nextLong(boundMin, boundMax), ChronoUnit.SECONDS);
    }

    @Override
    public void setBoundMin(Long boundMin) {
        this.boundMin = boundMin;
    }

    @Override
    public void setBoundMax(Long boundMax) {
        this.boundMax = boundMax;
    }

}
