package com.oncemore.store.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CartDTO {
    private UUID cartId;
    private List<CartItemDTO> cartItemDTOList;
    private BigDecimal totalPrice;
}
