package com.getgoodm.currencyconverter;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Сервис для вычисления результатов конвертаций
@Service("calculateService")
public class CalculateService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final HistoryRepository historyRepository;

    public CalculateService(CurrencyRepository currencyRepository, CurrencyRateRepository currencyRateRepository,
                            HistoryRepository historyRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyRateRepository = currencyRateRepository;
        this.historyRepository = historyRepository;
    }
    public double  calculateValue( String firstCurrency,  String secondCurrency, double amount) {
        //Получение информации о валютах из таблицы валют БД
        Currency c1 = currencyRepository.findByCharCode(firstCurrency);
        int n1 = c1.getNominal();
        Currency c2 = currencyRepository.findByCharCode(secondCurrency);
        int n2 = c2.getNominal();
        LocalDate currentDate = LocalDate.now();
        CurrencyRate cr1 = null, cr2 = null;
        DataFromXML data;
        //Попытка получить курсы валют из таблицы курсов БД на сегодняшний день
        Optional<CurrencyRate> ocr1 = currencyRateRepository.findByDateAndCharCode(currentDate, firstCurrency);
        if (ocr1.isPresent()) {
            cr1 = ocr1.get();
        }
        else if (firstCurrency.equals("RUB")) {
            cr1 = currencyRateRepository.findByCharCode("RUB");
        }
        Optional<CurrencyRate> ocr2 = currencyRateRepository.findByDateAndCharCode(currentDate, secondCurrency);
        if (ocr2.isPresent()) {
            cr2 = ocr2.get();
        }

        else if (secondCurrency.equals("RUB")) {
            cr2 = currencyRateRepository.findByCharCode("RUB");
        }
        //Если в БД отсутствует курс валют на сегодняшний день
        if (cr1 == null || cr2 == null){
            //Получение актуальной даты с сайта ЦБ с помощью сервиса парсинга XML
            LocalDate actualDate = XMLService.getActualDate();
            //Получение "самой свежей" даты из БД
            LocalDate lastBaseDate = currencyRateRepository.findTopByOrderByIdDesc().getDate();
            System.out.println("Последняя дата из БД: " + lastBaseDate);
            //Если 2 полученные даты отличаются, в таблицу курсов базы данных добавляются актуальные курсы с сайта ЦБ
            if (!actualDate.equals(lastBaseDate)) {
                data = XMLService.parseRates();
                for (CurrencyRate cr : data.getCurrencyRates()) {
                    if (cr.getCharCode().equals(firstCurrency)) {
                        cr1 = cr;
                    }
                    if (cr.getCharCode().equals(secondCurrency)) {
                        cr2 = cr;
                    }
                    currencyRateRepository.save(cr);
                }
            }
            //Если 2 полученные даты совпадают, курсы валют считываются из БД
            else {
                Optional<CurrencyRate> opcr1 = currencyRateRepository.findByDateAndCharCode(actualDate, firstCurrency);
                if (opcr1.isPresent())
                    cr1 = opcr1.get();
                Optional<CurrencyRate> opcr2 = currencyRateRepository.findByDateAndCharCode(actualDate, secondCurrency);
                if (opcr2.isPresent())
                    cr2 = opcr2.get();
            }
        }
        System.out.println("cr1" + cr1 + "cr2" + cr2);
        //Вычисление резульатата конвертации
        double res = amount*cr1.getRate()/n1/cr2.getRate()*n2;
        res = Math.round(res*100.0)/100.0;
        Conversion conversion = new Conversion(c1.getCharCode(), c2.getCharCode(), amount, res, currentDate);
        System.out.println(conversion);
        //Сохранение конвертации в таблице истории конвертаций БД
        historyRepository.save(conversion);
        return res;

    }
    public List<Conversion> getHistory(String firstCurrency, String secondCurrency, LocalDate date) {
        return historyRepository.findByFirstCurrencyAndSecondCurrencyAndDate(firstCurrency, secondCurrency, date);
    }
    public Iterable <Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }
}
