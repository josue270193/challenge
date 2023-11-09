package app.josue.challengecalculator.infrastructure.outbound.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String LOG_TOPIC = "log_topic";
    private final int PARTITIONS = 5;
    private final int REPLICAS = 1;

    @Bean
    public NewTopic logTopic() {
        return TopicBuilder.name(LOG_TOPIC)
                .partitions(PARTITIONS)
                .replicas(REPLICAS)
                .build();
    }

}
