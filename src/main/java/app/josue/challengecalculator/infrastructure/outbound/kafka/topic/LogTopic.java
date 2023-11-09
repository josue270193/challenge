package app.josue.challengecalculator.infrastructure.outbound.kafka.topic;

import java.time.OffsetDateTime;

public record LogTopic(

        String httpMethod,
        String path,
        String requestBody,
        String responseBody,
        OffsetDateTime createdAt

) {
}
