package com.example.weather.model;

public enum FrequencyUnit {
    SECOND("second"),
    MINUTE("minute"),
    HOUR("hour"),
    DAY("day");

    private String unit;

    FrequencyUnit(String unit){
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
