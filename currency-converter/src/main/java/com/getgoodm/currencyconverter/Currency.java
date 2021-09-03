package com.getgoodm.currencyconverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Currency {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;
    @Column
    private String numCode;
    @Column
    private String charCode;
    @Column
    private int nominal;
    @Column
    private String name;

    public Currency() {}
    public Currency(String id, String numCode, String charCode, int nominal, String name) {
        this.id = id;
        this.charCode = charCode;
        this.numCode = numCode;
        this.nominal = nominal;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", charCode='" + charCode + '\'' +
                ", numCode='" + numCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                '}';
    }
}
