package com.example.dailyhealth;

public class BottleInfo {
    private boolean status;
    private String amount;

    public BottleInfo(boolean stt, String amount) {
        this.amount = amount;
        this.status = stt;
    }

    public String getAmount() {
        return amount;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
