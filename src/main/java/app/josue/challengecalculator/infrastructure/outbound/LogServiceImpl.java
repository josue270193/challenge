package app.josue.challengecalculator.infrastructure.outbound;

import app.josue.challengecalculator.domain.model.LogModel;
import app.josue.challengecalculator.domain.service.LogService;
import app.josue.challengecalculator.infrastructure.outbound.kafka.producer.LogProducer;
import app.josue.challengecalculator.infrastructure.outbound.postgres.LogPostgresRepository;
import app.josue.challengecalculator.infrastructure.outbound.postgres.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Component
public class LogServiceImpl implements LogService {

    @Autowired
    private LogPostgresRepository logPostgresRepository;

    @Autowired
    private LogProducer logProducer;

    @Autowired
    private Scheduler scheduler;

    @Override
    public LogModel save(LogModel logModel) {
        logProducer.sendMessage(logModel.httpMethod(), logModel.path(), logModel.requestBody(), logModel.responseBody(), logModel.createdAt());
        return logModel;
    }

    @Override
    public Mono<LogModel> saveEntity(LogModel logModel) {
        return Mono.just(logModel)
                .map(LogEntity::new)
                .flatMap(entity -> Mono.fromCallable(() -> logPostgresRepository.save(entity)))
                .publishOn(scheduler)
                .map(LogEntity::toModel);
    }

    @Override
    public Mono<Page<LogModel>> getAll(Pageable pageable) {
        return Mono.just(pageable)
                .flatMap(request -> Mono.fromCallable(() -> logPostgresRepository.findAll(request)))
                .publishOn(scheduler)
                .map(entity -> {
                    var list = entity.stream()
                            .map(LogEntity::toModel)
                            .toList();
                    return new PageImpl<>(list, entity.getPageable(), entity.getTotalElements());
                });
    }
}
