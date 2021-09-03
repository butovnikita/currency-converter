package com.getgoodm.currencyconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ConverterController {
    @Autowired
    CalculateService calculateService;

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@RequestBody ConvertForm convertForm) {
        AjaxResponseBody result = new AjaxResponseBody();
        double convertResult = calculateService.calculateValue(convertForm.getFirstCurrency(), convertForm.getSecondCurrency(),
                convertForm.getAmount());
        result.setConvertResult(convertResult);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/convert")
    public ModelAndView getData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("converter");
        Iterable <Currency> currencies = calculateService.getAllCurrencies();
        mv.addObject("currencies", currencies);
        return mv;
    }

    @PostMapping("/history")
    public ResponseEntity<?>  getHistory(@RequestBody HistoryForm historyForm) {
        System.out.println("HISTORY HERE");
        AjaxHistoryBody result = new AjaxHistoryBody();
        List<Conversion> conversions = calculateService.getHistory(historyForm.getFirstCurrency(), historyForm.getSecondCurrency(),
                historyForm.getDate());
        System.out.println(conversions);
        result.setConversions(conversions);
        return ResponseEntity.ok(result);
    }

}
