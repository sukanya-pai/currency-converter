package org.example.service;

import org.example.exception.ExchangeRateNotFoundException;
import org.example.exception.InvalidDataException;
import org.example.model.Currency;
import org.example.model.CurrencyConvertDTO;
import org.example.utils.CurrencyRatesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConverterService {

    @Autowired
    HashMap<String,String> currencyRateMap;

    /**
     * Get Exchange rate of currency pairs
     * @param currency
     * @param baseCurrency
     * @return
     * @throws ExchangeRateNotFoundException
     */
    public Double getExchangeRate(String currency, String baseCurrency) throws ExchangeRateNotFoundException {
        Double baseCurrencyExchangeRate = null;
        if(baseCurrency==null||baseCurrency.isBlank()|| baseCurrency.isEmpty() || CurrencyRatesConstants.EUR.equalsIgnoreCase(baseCurrency)){
            baseCurrency= CurrencyRatesConstants.EUR;
            baseCurrencyExchangeRate = 1.0d;
        }
        Double currencyExchangeRateECB = Double.valueOf(currencyRateMap.getOrDefault(currency.toUpperCase(),"-1"));
        if(!CurrencyRatesConstants.EUR.equalsIgnoreCase(baseCurrency))
            baseCurrencyExchangeRate = Double.valueOf(currencyRateMap.getOrDefault(baseCurrency.toUpperCase(),"-1"));

        if(currencyExchangeRateECB==-1 || baseCurrencyExchangeRate==-1){
            throw new ExchangeRateNotFoundException(CurrencyRatesConstants.EXCHANGE_RATE_NOT_FOUND);
        }
        return roundOffRateToFourPlaces(baseCurrencyExchangeRate/currencyExchangeRateECB);
    }

    private Double roundOffRateToFourPlaces(Double rate){
        return (double)Math.round(rate * 10000d) / 10000d;
    }

    /**
     * Return value of amount
     * @param currencyConvertDTO
     * @return
     * @throws ExchangeRateNotFoundException
     * @throws InvalidDataException
     */
    public Currency getCurrencyConversion(CurrencyConvertDTO currencyConvertDTO) throws ExchangeRateNotFoundException, InvalidDataException {
        if(!validateCurrencyDTO(currencyConvertDTO)){
            throw new InvalidDataException(CurrencyRatesConstants.INVALID_DATA);
        }
        Double exchangeRateVal = getExchangeRate(currencyConvertDTO.getTargetCurrency().getUnit(), currencyConvertDTO.getBaseCurrencyUnit());
        Double targetAmount = currencyConvertDTO.getTargetCurrency().getValue();
        return new Currency(roundOffRateToFourPlaces(targetAmount*exchangeRateVal) , currencyConvertDTO.getBaseCurrencyUnit().toUpperCase());
    }

    /**
     * Validate currency DTO object
     * Here, if object is null or base currency unit and target currency unit is not specified,
     * it is considered invalid
     * @param currencyConvertDTO
     * @return
     */
    private boolean validateCurrencyDTO(CurrencyConvertDTO currencyConvertDTO) {
        if(currencyConvertDTO==null ||
                currencyConvertDTO.getBaseCurrencyUnit()==null || currencyConvertDTO.getBaseCurrencyUnit()==""  ||
                currencyConvertDTO.getTargetCurrency()==null ||
                currencyConvertDTO.getTargetCurrency().getUnit()==null || currencyConvertDTO.getTargetCurrency().getUnit()=="") return false;
        return true;
    }

    /**
     * To return list of currencies supported
     * @return
     */
    public Set<String> getSupportedCurrencies() {
        if(currencyRateMap==null) return new HashSet<>();
        return currencyRateMap.keySet();
    }
}
