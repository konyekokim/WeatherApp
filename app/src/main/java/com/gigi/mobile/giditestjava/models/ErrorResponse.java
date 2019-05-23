package com.gigi.mobile.giditestjava.models;

public class ErrorResponse {
    private String cod = "";
    private String message = "";

    public ErrorResponse(String cod, String message) {
        this.cod = cod;
        this.message = message;
    }

    public ErrorResponse() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "cod='" + cod + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

