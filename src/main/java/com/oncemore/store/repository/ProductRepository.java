package com.oncemore.store.repository;

import com.oncemore.store.dto.ProductDTO;
import com.oncemore.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByStatus(boolean status);

    Product findByIdAndStatus(UUID id, boolean status);

    @Query(value = "select new com.oncemore.store.dto.ProductDTO ( p.id, p.name, p.price, pi.url) from Product p " +
            "left join ProductImage pi on p.id = pi.productId and pi.isPrimary = :active where p.status = :active and p.quantity > 0")
    List<ProductDTO> getAllProductBy(@Param("active") boolean active);

    @Query("SELECT new com.oncemore.store.dto.ProductDTO ( p.id, p.name, p.price, pi.url) FROM Product p " +
            " left JOIN ProductCategory pc ON p.id = pc.productId " +
            " left JOIN Category c ON pc.categoryId = c.id " +
            " left join ProductImage pi on p.id = pi.productId and pi.isPrimary = :active " +
            " WHERE " +
            " (:minPrice IS NULL OR p.price >= :minPrice)" +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) AND (:categoryId IS NULL OR pc.categoryId = :categoryId)")

    List<ProductDTO> filterByCategoryAndPrice(@Param("categoryId") UUID categoryId,
                                           @Param("minPrice") BigDecimal minPrice,
                                           @Param("maxPrice") BigDecimal maxPrice,
                                              @Param("active") boolean active);

}
