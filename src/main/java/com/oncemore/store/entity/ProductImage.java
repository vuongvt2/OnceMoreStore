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
@Table(name = "ProductImage")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "ProductId", nullable = false)
//    private Product product;

    @Column(name = "ProductId", nullable = false)
    private UUID productId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Url", nullable = false)
    private String url;

    @Column(name = "IsPrimary", nullable = false)
    private boolean isPrimary;
}
