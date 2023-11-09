package app.josue.challengecalculator.domain.model;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public enum OperationTypeModel {

    ADD("add");

    private String name;

    OperationTypeModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OperationTypeModel getByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return Arrays.stream(OperationTypeModel.values())
                .filter(item -> item.getName().compareToIgnoreCase(name) == 0)
                .findFirst()
                .orElse(null);
    }

}
