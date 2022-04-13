package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyConvertDTO {
    private Currency targetCurrency;
    private String baseCurrencyUnit;
}
