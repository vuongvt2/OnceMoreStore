package com.oncemore.store.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Status")
    private boolean status;

    @CreatedDate
    @Column(name = "CreateAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date createAt;

    @Transient
    private List<UUID> categoryIds;

    @Transient
    private List<String> imageUrls;
}
