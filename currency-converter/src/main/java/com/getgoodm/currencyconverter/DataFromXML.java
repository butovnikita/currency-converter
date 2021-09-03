package com.getgoodm.currencyconverter;
import java.util.List;

public class DataFromXML {
    private List<Currency> currencies;
    private List<CurrencyRate> currencyRates;

    public DataFromXML(List<Currency> currencies, List<CurrencyRate> currencyRates) {
        this.currencies = currencies;
        this.currencyRates = currencyRates;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }
}
