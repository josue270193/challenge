package app.josue.challengecalculator.application.config;

import app.josue.challengecalculator.application.controller.CalculatorController;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricHandler;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricTagResult;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DebugMetricHandler implements MetricHandler {
    private final Logger log = LoggerFactory.getLogger(DebugMetricHandler.class);

    @Override
    public void handle(MetricType type, String name, long tokens, List<MetricTagResult> tags) {
        log.info(String.format("type: %s; name: %s; tags: %s",
                type,
                name,
                tags
                        .stream()
                        .map(mtr -> mtr.getKey() + ":" + mtr.getValue())
                        .collect(Collectors.joining(","))));

    }

}