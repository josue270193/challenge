package app.josue.challengecalculator.application.service;

import app.josue.challengecalculator.application.dto.request.AddRequestDto;
import app.josue.challengecalculator.application.dto.response.AddResponseDto;
import reactor.core.publisher.Mono;

public interface CalculatorService {

    Mono<AddResponseDto> add(AddRequestDto request);

}
