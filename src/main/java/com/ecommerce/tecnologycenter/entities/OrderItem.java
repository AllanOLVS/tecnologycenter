package com.ecommerce.tecnologycenter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_orderPayment")
public class OrderItem {

    private Integer quantity;
    private Double price;


}
