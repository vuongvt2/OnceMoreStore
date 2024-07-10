package com.oncemore.store.repository;

import com.oncemore.store.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    @Query("select pi.url from ProductImage pi where pi.productId = :productId")
    List<String> findAllUrlByProductId(UUID productId);

    void deleteAllByProductId(UUID productId);

    @Query("select pi.url from ProductImage pi where  pi.productId = :productId and pi.isPrimary = true ")
    String findUrlByProductId(UUID productId);
}
