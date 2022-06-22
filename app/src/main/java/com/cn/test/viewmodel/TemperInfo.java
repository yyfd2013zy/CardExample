package com.cn.test.viewmodel;

public class TemperInfo {
    private String time;
    private String temperture;
    private String temperture2;

    public TemperInfo(String time,String temperture,String temperture2) {
        this.time = time;
        this.temperture = temperture;
        this.temperture2 = temperture2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperture() {
        return temperture;
    }

    public void setTemperture(String temperture) {
        this.temperture = temperture;
    }

    public String getTemperture2() {
        return temperture2;
    }

    public void setTemperture2(String temperture2) {
        this.temperture2 = temperture2;
    }
}
