package com.getgoodm.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CurrencyConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
	@Bean
	ApplicationRunner init(CurrencyRepository repository, CurrencyRateRepository rateRepository){
		//При запуске приложения происходит считывание актуальных курсов валют с сайта ЦБ с помощью сервиса парсинга XML
		DataFromXML data = XMLService.parseRates();
		return args -> {
			repository.save(new Currency("1", "111", "RUB", 1, "Российский рубль"));
			for (Currency c:data.getCurrencies())
				repository.save(c);
			rateRepository.save(new CurrencyRate("1", null, "RUB", 1.0));
			for (CurrencyRate cr:data.getCurrencyRates())
				rateRepository.save(cr);
		};
	}

}
