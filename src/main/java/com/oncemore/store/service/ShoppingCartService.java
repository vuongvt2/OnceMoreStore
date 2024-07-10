package com.oncemore.store.service;

import com.oncemore.store.dto.CartDTO;
import com.oncemore.store.dto.OrderDTO;
import com.oncemore.store.dto.ResponseOrder;

import java.util.UUID;

public interface ShoppingCartService {
    String addProductToCart(UUID productId, int quantity);

    CartDTO viewCart();

    void removeProductToCart(UUID productId);

    ResponseOrder processOrder(OrderDTO orderDTO, boolean orderNow);

    CartDTO buyNowProduct(UUID productId) throws Exception;
}
