package org.example.request.transfer;

import org.springframework.stereotype.Component;

@Component
public class Amount {
    private int value;
    private String currency;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
