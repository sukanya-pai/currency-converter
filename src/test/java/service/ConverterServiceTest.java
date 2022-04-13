package service;

import org.example.service.ConverterService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class ConverterServiceTest {

    /**
     * Test case to test list of supported currencies
     */
    @Test
    public void getSupportedCurrenciesTest() {
        ConverterService converterService = new ConverterService();
        Set<String> supportedList = converterService.getSupportedCurrencies();
        Assert.assertEquals(0,supportedList.size());
    }
}
