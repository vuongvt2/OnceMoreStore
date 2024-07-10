package com.oncemore.store.service.impl;

import com.oncemore.store.dto.ProductDTO;
import com.oncemore.store.entity.Product;
import com.oncemore.store.entity.ProductCategory;
import com.oncemore.store.entity.ProductImage;
import com.oncemore.store.model.ProductModel;
import com.oncemore.store.repository.ProductCategoryRepository;
import com.oncemore.store.repository.ProductImageRepository;
import com.oncemore.store.repository.ProductRepository;
import com.oncemore.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private static final String IMAGE_DIR = "src/main/resources/static/images/";

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Transactional
    @Override
    public void save(ProductModel productModel, List<MultipartFile> images) {
        Product product = Product.builder()
                .name(productModel.getName())
                .description(productModel.getDescription())
                .price(productModel.getPrice())
                .quantity(productModel.getQuantity())
                .status(true)
                .build();
        product = repository.save(product);
        productModel.setId(product.getId());
        this.saveProductImageAndCategories(productModel,images);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAllByStatus(true);
    }

    @Override
    @Transactional
    public String delete(UUID id) {
        try {
            Product product = repository.getById(id);
            product.setStatus(false);
            repository.save(product);
        } catch (Exception e) {
            return "Có lỗi xảy ra!";
        }

        return "Xóa thành công!";
    }

    @Override
    public Product findById(UUID id) {
        Product product = repository.findByIdAndStatus(id, true);
        if (Objects.nonNull(product)) {
            List<UUID> categoryIds = productCategoryRepository.findAllCategoryIdByProductId(product.getId());
            product.setCategoryIds(categoryIds);
            List<String> imageUrls = productImageRepository.findAllUrlByProductId(product.getId());
            product.setImageUrls(imageUrls);
        }
        return product;
    }

    @Override
    @Transactional
    public void update(ProductModel productModel, List<MultipartFile> images){
        Product product = repository.findById(productModel.getId()).orElseThrow();
        product.setName(productModel.getName());
        product.setDescription(productModel.getDescription());
        product.setPrice(productModel.getPrice());
        product.setQuantity(productModel.getQuantity());
        product.setStatus(true);
        product = repository.save(product);
        productImageRepository.deleteAllByProductId(productModel.getId());
        productImageRepository.flush();
        productCategoryRepository.deleteAllByProductId(productModel.getId());
        productCategoryRepository.flush();
        this.saveProductImageAndCategories(productModel, images);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repository.getAllProductBy(true);
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        Product product = repository.getById(id);
        List<String> urlImageList = productImageRepository.findAllUrlByProductId(product.getId());
        String activeImage = "";
        if (!CollectionUtils.isEmpty(urlImageList)) {
            activeImage = urlImageList.get(0);
        }
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .description(product.getDescription())
                .url(activeImage)
                .urlImageList(urlImageList)
                .build();
        return productDTO;
    }

    @Override
    public List<ProductDTO> filterProductsByCategoryAndPrice(UUID categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        if (UUID.fromString("11111111-1111-1111-1111-111111111111").equals(categoryId)) {
            categoryId = null;
        }
        return repository.filterByCategoryAndPrice(categoryId, minPrice, maxPrice, true);
    }

    private void saveProductImageAndCategories(ProductModel productModel, List<MultipartFile> images) {
        List<String> fileNames = saveImages(images);
        for (String name : fileNames) {
            ProductImage productImage = ProductImage.builder()
                    .name(name)
                    .productId(productModel.getId())
                    .url(name)
                    .isPrimary(name.equals(fileNames.get(0)) ? true : false)
                    .build();
            productImageRepository.save(productImage);
        }
        for (UUID categoryId : productModel.getCategoryIds()) {
            ProductCategory productCategory = ProductCategory.builder()
                    .categoryId(categoryId)
                    .productId(productModel.getId())
                    .build();
            productCategoryRepository.save(productCategory);
        }
    }

    private List<String> saveImages(List<MultipartFile> images) {
        List<String> savedImageNames = new ArrayList<>();

        File directory = new File(IMAGE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    byte[] bytes = image.getBytes();
                    String originalFilename = image.getOriginalFilename();
                    Path path = Paths.get(IMAGE_DIR + originalFilename);
                    Files.write(path, bytes);
                    savedImageNames.add(originalFilename);
                } catch (IOException e) {
                }
            }
        }

        return savedImageNames;
    }

}
