package com.oncemore.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "CartId", nullable = false)
//    private Cart cart;
    @Column(name = "cartId",nullable = false)
    private UUID cartId;
//    @ManyToOne
//    @JoinColumn(name = "ProductId", nullable = false)
//    private Product product;P

    @Column(name = "productId",nullable = false)
    private UUID productId;

    @Column(name = "Quantity", nullable = false)
    private int quantity;
}
