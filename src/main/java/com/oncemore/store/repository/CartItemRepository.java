package com.oncemore.store.repository;

import com.oncemore.store.dto.CartItemDTO;
import com.oncemore.store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    CartItem findByCartIdAndProductId(UUID cartId, UUID productId);

    @Query("select new com.oncemore.store.dto.CartItemDTO(ci.id, ci.productId, ci.quantity,p.name,p.description,p.price, p.status, pi.url, p.quantity) " +
            "from CartItem ci left join Product p " +
            "on ci.productId = p.id left join ProductImage pi on p.id = pi.productId and pi.isPrimary = true where ci.cartId = :cartId  ")
    List<CartItemDTO> findAllByCartId(UUID cartId);

    void deleteAllByCartId(UUID cartId);
}
