package com.getgoodm.currencyconverter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Conversion {
    @Id @GeneratedValue
    @Column
    private Long id;
    @Column
    private String firstCurrency;
    @Column
    private String secondCurrency;
    @Column
    private double firstValue;
    @Column
    private double secondValue;
    @Column
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(String firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public String getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(String secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public double getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(double firstValue) {
        this.firstValue = firstValue;
    }

    public double getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(double secondValue) {
        this.secondValue = secondValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Conversion(String firstCurrency, String secondCurrency, double firstValue, double secondValue, LocalDate date) {
        this.id = id;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", firstCurrency='" + firstCurrency + '\'' +
                ", secondCurrency='" + secondCurrency + '\'' +
                ", firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                ", date=" + date +
                '}';
    }

    public Conversion() {
    }

}
