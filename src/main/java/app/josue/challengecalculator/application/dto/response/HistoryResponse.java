package app.josue.challengecalculator.application.dto.response;

import java.util.List;

public record HistoryResponse(

        List<HistoryItemResponse> content,
        Integer total

) {
}
