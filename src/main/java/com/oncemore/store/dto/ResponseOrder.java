package com.oncemore.store.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseOrder {
    private String resultCode;
    private BigDecimal amount;
    private String orderCode;
    private boolean isPayment;
}
