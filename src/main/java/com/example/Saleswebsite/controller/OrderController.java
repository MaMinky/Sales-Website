package com.example.Saleswebsite.controller;

import com.example.Saleswebsite.entity.Order;
import com.example.Saleswebsite.entity.User;
import com.example.Saleswebsite.repository.UserRepository;
import com.example.Saleswebsite.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/checkout")
    public Order checkout(
            @RequestParam Integer userId,
            @RequestParam String address,
            @RequestParam String phone) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        return orderService.checkout(user, address, phone);
    }
}
