package com.oncemore.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CartItemDTO {
    private UUID cartItemId;
    private UUID productId;
    private int quantity;
    private String nameProduct;
    private String description;
    private BigDecimal price;
    private boolean statusProduct;
    private String url;
    private int maxQuantity;

    public CartItemDTO(UUID cartItemId,UUID productId, int quantity, String nameProduct, String description,
                       BigDecimal price, boolean statusProduct, String url, int maxQuantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.nameProduct = nameProduct;
        this.description = description;
        this.price = price;
        this.statusProduct = statusProduct;
        this.url = url;
        this.maxQuantity = maxQuantity;

    }
}
