package com.example.SpirngSecEx.payload.request;

public class SupporterRequest {
    private double amount;

    // Constructors, getters and setters
    public SupporterRequest() {
    }

    public SupporterRequest(double amount) {
        this.amount = amount;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
