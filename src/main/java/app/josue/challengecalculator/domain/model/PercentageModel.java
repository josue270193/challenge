package app.josue.challengecalculator.domain.model;

import java.io.Serial;
import java.io.Serializable;

public record PercentageModel (
        Double percentage
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
