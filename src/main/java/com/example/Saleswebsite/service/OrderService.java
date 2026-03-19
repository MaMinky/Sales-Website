package com.example.Saleswebsite.service;

import com.example.Saleswebsite.entity.*;
import com.example.Saleswebsite.enums.OrderStatus;
import com.example.Saleswebsite.repository.OrderItemRepository;
import com.example.Saleswebsite.repository.OrderRepository;
import com.example.Saleswebsite.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;

    public Order checkout(User user, String address, String phone) {
        // 1. Lưu thông tin đơn hàng chung
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPhone(phone);
        order.setTotalPrice(cartService.getTotal());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);

        // 2. Lưu chi tiết từng món và CẬP NHẬT KHO
        for (CartItem item : cartService.getItems()) {
            // Lấy sản phẩm từ DB để cập nhật số lượng
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            // Kiểm tra kho
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng!");
            }

            // Trừ số lượng tồn kho
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // Lưu OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItemRepository.save(orderItem);
        }

        cartService.clear();
        return order;
    }

    public void updateStatus(Integer orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }
}
