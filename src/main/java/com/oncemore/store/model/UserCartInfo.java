package com.oncemore.store.model;

import com.oncemore.store.entity.Cart;
import com.oncemore.store.entity.User;
import com.oncemore.store.repository.CartRepository;
import com.oncemore.store.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Component
public class UserCartInfo {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    private UUID userId;
    private UUID cartId;

    public UUID getUserId() {
        if (userId == null) {
            reloadData();
        }
        return userId;
    }

    public UUID getCartId() {
        if (cartId == null) {
            reloadData();
        }
        return cartId;
    }

    private void reloadData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(authentication.getName());
            userId = user.getId();
            Cart cart = cartRepository.findByUserId(user.getId());
            cartId = cart.getId();
        }
    }
}
