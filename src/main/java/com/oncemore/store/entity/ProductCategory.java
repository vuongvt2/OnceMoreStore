package com.oncemore.store.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ProductCategory")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

    //    @ManyToOne
//    @JoinColumn(name = "CategoryId", nullable = false)
//    private Category category;
    @Column(name = "CategoryId", nullable = false)
    private UUID categoryId;

//    @ManyToOne
//    @JoinColumn(name = "ProductId", nullable = false)
//    private Product product;
    @Column(name = "ProductId", nullable = false)
    private UUID productId;
}
