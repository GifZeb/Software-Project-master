package com.example.n01204206.milestone;

public class DataStructure {

    private String temp;
    private String humidity;
    private String moisture;
    private String time;


    public DataStructure(){

    }
    public DataStructure( String temp, String humidity, String moisture, String time) {

        this.temp = temp;
        this.humidity = humidity;
        this.moisture = moisture;
        this.time = time;

    }


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getTime(){return time;}

    public void setTime(String time){this.time = time;}

}
