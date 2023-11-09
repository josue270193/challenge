package app.josue.challengecalculator.application.controller;

import app.josue.challengecalculator.application.dto.request.AddRequestDto;
import app.josue.challengecalculator.application.dto.response.AddResponseDto;
import app.josue.challengecalculator.application.service.CalculatorService;
import app.josue.challengecalculator.domain.model.LogModel;
import app.josue.challengecalculator.domain.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    private final Logger log = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private LogService logService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    public Mono<AddResponseDto> postAdd(
            @Valid @RequestBody AddRequestDto request,
            ServerHttpRequest httpRequest
    ) {
        return calculatorService.add(request)
                .doOnNext(response -> {
                    try {
                        log.info("Save info on log");
                        var logModel = LogModel.create(
                                httpRequest.getMethod().name(),
                                httpRequest.getPath().value(),
                                objectMapper.writeValueAsString(request),
                                objectMapper.writeValueAsString(response)
                        );
                        logService.save(logModel);
                    } catch (JsonProcessingException e) {
                        log.error("Error parsing to JSON to log", e);
                    }
                });
    }

}
