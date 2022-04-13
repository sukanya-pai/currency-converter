package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
public class CurrencyConverterConfig {

    @Bean
    public HashMap<String,String> getCurrencyRateMap(){
        return new HashMap<String,String>();
    }
}