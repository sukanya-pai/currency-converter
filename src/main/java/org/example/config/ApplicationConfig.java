package org.example.config;

import org.example.utils.ExchangeRateXMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent> {

    private static final String exchangeRateURL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Autowired
    HashMap<String,String> currencyRateMap;

    @Autowired
    ExchangeRateXMLConverter exchangeRateXMLConverter;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(exchangeRateURL, String.class);
        currencyRateMap.putAll(exchangeRateXMLConverter.exchangeRateConverter(response.getBody()));
    }
}