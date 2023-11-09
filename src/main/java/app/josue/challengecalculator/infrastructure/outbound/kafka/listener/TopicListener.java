package app.josue.challengecalculator.infrastructure.outbound.kafka.listener;

import app.josue.challengecalculator.domain.model.LogModel;
import app.josue.challengecalculator.domain.service.LogService;
import app.josue.challengecalculator.infrastructure.outbound.kafka.config.KafkaConfig;
import app.josue.challengecalculator.infrastructure.outbound.kafka.topic.LogTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TopicListener {

    private final Logger log = LoggerFactory.getLogger(TopicListener.class);

    @Autowired
    private LogService logService;

    @KafkaListener(topics = KafkaConfig.LOG_TOPIC)
    public void listenerTopic(LogTopic data) {
        Mono.just(data)
                .doOnNext(topic -> log.info("Topic: {}", topic))
                .map(topic -> new LogModel(
                        null,
                        data.httpMethod(),
                        data.path(),
                        data.requestBody(),
                        data.responseBody(),
                        data.createdAt()
                ))
                .flatMap(logService::saveEntity)
                .subscribe();
    }

}
