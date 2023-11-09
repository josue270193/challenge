package app.josue.challengecalculator.application.service.impl;

import app.josue.challengecalculator.application.dto.request.AddRequestDto;
import app.josue.challengecalculator.application.dto.response.AddResponseDto;
import app.josue.challengecalculator.application.service.CalculatorService;
import app.josue.challengecalculator.domain.model.LogModel;
import app.josue.challengecalculator.domain.service.PercentageClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static app.josue.challengecalculator.domain.model.OperationTypeModel.ADD;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Autowired
    private PercentageClientService percentageClientService;

    @Override
    public Mono<AddResponseDto> add(AddRequestDto request) {
        return percentageClientService.getPercentage()
                .map(model -> {
                    double result = request.value1() + request.value2();
                    result *= (1 + (model.percentage() / 100));
                    return BigDecimal.valueOf(result);
                })
                .map(AddResponseDto::new);
    }

}
