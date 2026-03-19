package com.example.Saleswebsite.controller;

import com.example.Saleswebsite.entity.Product;
import com.example.Saleswebsite.repository.ProductRepository;
import com.example.Saleswebsite.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Map<String, Object> viewCart() {
        return Map.of(
                "items", cartService.getItems(),
                "totalPrice", cartService.getTotal()
        );
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer productId, @RequestParam int qty) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        cartService.add(product, qty);
        return "Đã thêm " + product.getName() + " vào giỏ hàng!";
    }

    @PutMapping("/update")
    public String updateCart(@RequestParam Integer productId, @RequestParam int qty) {
        cartService.update(productId, qty);
        return "Đã cập nhật số lượng!";
    }

    @DeleteMapping("/remove/{productId}")
    public String removeCartItem(@PathVariable Integer productId) {
        cartService.remove(productId);
        return "Đã xóa khỏi giỏ hàng!";
    }
}
