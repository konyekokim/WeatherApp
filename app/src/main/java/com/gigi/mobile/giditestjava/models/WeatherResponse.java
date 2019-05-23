package com.gigi.mobile.giditestjava.models;

public class WeatherResponse {
    private CurrentResponse currentResponse;
    private String errorMessage = "";

    public WeatherResponse(CurrentResponse currentResponse, String errorMessage) {
        this.currentResponse = currentResponse;
        this.errorMessage = errorMessage;
    }

    public WeatherResponse() {
    }

    public CurrentResponse getCurrentResponse() {
        return currentResponse;
    }

    public void setCurrentResponse(CurrentResponse currentResponse) {
        this.currentResponse = currentResponse;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "currentResponse=" + currentResponse +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
