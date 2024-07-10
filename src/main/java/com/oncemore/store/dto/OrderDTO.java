package com.oncemore.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {
    @NotEmpty
    private String address;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String receiverName;

    @NotNull
    private BigDecimal amount;
    private String isPayment;
    private List<OrderDetailDTO> orderDetailDTOList;
}
