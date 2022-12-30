package com.example.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplicationType {

    EMPLOYEE(1),
    COMPANY(2);

    private final String entity;
    private final Integer priority;

    ApplicationType(Integer priority) {
        this.entity = name();
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}
