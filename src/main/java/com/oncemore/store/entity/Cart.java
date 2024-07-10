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
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "UserId", referencedColumnName = "Id", unique = true)
//    private User user;

    @Column(name = "userId", unique = true)
    private UUID userId;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;
}
