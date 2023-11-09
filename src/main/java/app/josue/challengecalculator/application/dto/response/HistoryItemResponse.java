package app.josue.challengecalculator.application.dto.response;

import java.time.OffsetDateTime;

public record HistoryItemResponse(
        Long id,
        OffsetDateTime createdAt
) {
}
