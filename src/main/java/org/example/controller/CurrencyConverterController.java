package org.example.controller;

import org.example.exception.ExchangeRateNotFoundException;
import org.example.exception.InvalidDataException;
import org.example.model.Currency;
import org.example.model.CurrencyConvertDTO;
import org.example.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
@RequestMapping("currency")
public class CurrencyConverterController {

    @Autowired
    ConverterService converterService;

    /**
     * API to get exchange rate of given pair
     * @param currency
     * @param baseCurrency
     * @return
     */
    @GetMapping("/exchangerate")
    @ResponseBody
    public ResponseEntity<?> getExchangeRate(@RequestParam(name = "currency", required=true) String currency,
                                 @RequestParam(name = "baseCurrency", required=false) String baseCurrency ) {
        try{
            Double exchangeRate = converterService.getExchangeRate(currency,baseCurrency);
            return new ResponseEntity<>(exchangeRate, HttpStatus.OK);
        }catch(ExchangeRateNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Convert currency amount
     * @param currencyConvertDTO
     * @return
     */
    @PostMapping("/convert")
    @ResponseBody
    public ResponseEntity<?> convertCurrency(@RequestBody CurrencyConvertDTO currencyConvertDTO ) {
        try{
            Currency convertedCurrency = converterService.getCurrencyConversion(currencyConvertDTO);
            return new ResponseEntity<>(convertedCurrency.toString(), HttpStatus.OK);
        }catch(ExchangeRateNotFoundException | InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/supported")
    @ResponseBody
    public ResponseEntity<?> getSupportedCurrencies() {
        Set<String> supportedCurrencies = converterService.getSupportedCurrencies();
        return new ResponseEntity<>(supportedCurrencies, HttpStatus.OK);
    }
}
