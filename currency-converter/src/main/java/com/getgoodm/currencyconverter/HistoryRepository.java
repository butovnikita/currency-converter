package com.getgoodm.currencyconverter;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends CrudRepository<Conversion, Long> {
    List<Conversion> findByFirstCurrencyAndSecondCurrencyAndDate(String firstCurrency, String secondCurrency, LocalDate date);
}
