package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String unit;
    private Double value;

    public Currency(Double value, String unit) {
        this.unit = unit;
        this.value = value;
    }

    @Override
    public String toString() {
        return value +" "+ unit;
    }
}
