package app.josue.challengecalculator.domain.model;

import java.time.OffsetDateTime;

public record LogModel(
        Long id,
        String httpMethod,
        String path,
        String requestBody,
        String responseBody,
        OffsetDateTime createdAt
) {

    public static LogModel create(String httpMethod, String path, String requestBody, String responseBody) {
        return new LogModel(
                null,
                httpMethod,
                path,
                requestBody,
                responseBody,
                OffsetDateTime.now()
        );
    }

}
