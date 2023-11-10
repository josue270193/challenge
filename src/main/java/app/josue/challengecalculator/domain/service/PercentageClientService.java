package app.josue.challengecalculator.domain.service;

import app.josue.challengecalculator.domain.model.PercentageModel;
import reactor.core.publisher.Mono;

public interface PercentageClientService {

    Mono<PercentageModel> getPercentage();

    void setBoundMin(Long boundMin);

    void setBoundMax(Long boundMax);
}
