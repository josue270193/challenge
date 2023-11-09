package app.josue.challengecalculator.domain.service;

import app.josue.challengecalculator.domain.model.LogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface LogService {

    LogModel save(LogModel model);

    Mono<LogModel> saveEntity(LogModel model);

    Mono<Page<LogModel>> getAll(Pageable pageRequest);
}
