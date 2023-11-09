package app.josue.challengecalculator.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public record LogItemResponseDto(
        Long id,
        String httpMethod,
        String path,
        String requestBody,
        String responseBody,
        @Schema(type = "string", example = "2023-01-31T23:59:59Z")
        OffsetDateTime createdAt
) {
}
