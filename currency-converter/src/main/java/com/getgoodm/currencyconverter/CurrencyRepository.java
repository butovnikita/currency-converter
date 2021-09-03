package com.getgoodm.currencyconverter;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
    Currency findByCharCode(String charCode);
}
