package app.josue.challengecalculator.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddRequestDto(

        @NotNull(message = "Notnull_value")
        Integer value1,

        @NotNull(message = "Notnull_value")
        Integer value2

) {
}
