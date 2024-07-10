package com.oncemore.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

    //    @ManyToOne
//    @JoinColumn(name = "OrderId", nullable = false)
//    private Order order;
    @Column(name = "OrderId", nullable = false)
    private UUID orderId;

    //    @ManyToOne
//    @JoinColumn(name = "ProductId", nullable = false)
//    private Product product;
    @Column(name = "ProductId", nullable = false)
    private UUID productId;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;
}
