package app.josue.challengecalculator.infrastructure.outbound.kafka.producer;

import app.josue.challengecalculator.infrastructure.outbound.kafka.config.KafkaConfig;
import app.josue.challengecalculator.infrastructure.outbound.kafka.topic.LogTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;

@Component
public class LogProducer {

    @Autowired
    private KafkaTemplate<String, LogTopic> kafkaTemplate;

    public void sendMessage(String httpMethod, String path, String requestBody, String responseBody, OffsetDateTime createdAt) {
        LogTopic topic = null;
        if (StringUtils.hasText(httpMethod) &&
            StringUtils.hasText(path) &&
            createdAt != null
        ) {
            topic = new LogTopic(httpMethod, path, requestBody, responseBody, createdAt);
        }

        if (topic != null) {
            Message<LogTopic> message = MessageBuilder
                    .withPayload(topic)
                    .setHeader(KafkaHeaders.TOPIC, KafkaConfig.LOG_TOPIC)
                    .build();
            kafkaTemplate.send(message);
        }
    }

}
