package com.oncemore.store.service;

import com.oncemore.store.dto.ProductDTO;
import com.oncemore.store.entity.Product;
import com.oncemore.store.model.ProductModel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    void save(ProductModel product, List<MultipartFile> images);

    List<Product> findAll();

    String delete(UUID id);

    Product findById(UUID id);

    void update(ProductModel product, List<MultipartFile> images) throws Exception;

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(UUID id);

    List<ProductDTO> filterProductsByCategoryAndPrice(UUID categoryId, BigDecimal minPrice, BigDecimal maxPrice);
}
