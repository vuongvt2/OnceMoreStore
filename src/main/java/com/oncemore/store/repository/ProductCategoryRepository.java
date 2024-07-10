package com.oncemore.store.repository;

import com.oncemore.store.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {

    @Query("select pc.categoryId from ProductCategory pc where pc.productId = :productId")
    List<UUID> findAllCategoryIdByProductId(UUID productId);

    void deleteAllByProductId(UUID productId);
}
