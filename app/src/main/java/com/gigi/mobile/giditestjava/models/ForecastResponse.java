package com.gigi.mobile.giditestjava.models;

public class ForecastResponse {
    private Response response;
    private String errorMessage = "";

    public ForecastResponse(Response response, String errorMessage) {
        this.response = response;
        this.errorMessage = errorMessage;
    }

    public ForecastResponse() {
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
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
                "currentResponse=" + response +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
