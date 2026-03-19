package com.example.Saleswebsite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Integer productId;
    private String productName;
    private String imageUrl;
    private Double price;
    private Integer quantity;

    public Double getSubtotal() {
        return price * quantity;
    }
}
