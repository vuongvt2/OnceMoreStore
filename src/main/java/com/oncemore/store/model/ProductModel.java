package com.oncemore.store.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductModel {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private List<UUID> categoryIds;
    private List<MultipartFile> images;
}
