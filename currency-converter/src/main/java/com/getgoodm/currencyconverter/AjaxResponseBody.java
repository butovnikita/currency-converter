package com.getgoodm.currencyconverter;

public class AjaxResponseBody {
    double convertResult;
    String msg;
    public double getConvertResult() {
        return convertResult;
    }

    public void setConvertResult(double convertResult) {
        this.convertResult = convertResult;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
