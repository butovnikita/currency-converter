package com.getgoodm.currencyconverter;

import java.util.List;

public class AjaxHistoryBody {
    List<Conversion> conversions;
    String msg;

    public List<Conversion> getConversions() {
        return conversions;
    }

    public void setConversions(List<Conversion> conversions) {
        this.conversions = conversions;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
