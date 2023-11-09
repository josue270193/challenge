package app.josue.challengecalculator.application.dto.response;

import java.util.List;

public record LogResponseDto(

        List<LogItemResponseDto> content,
        int totalPages,
        long totalElements

) {
}
