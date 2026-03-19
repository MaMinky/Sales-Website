package com.example.Saleswebsite.repository;

import com.example.Saleswebsite.entity.Order;
import com.example.Saleswebsite.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUserIdOrderByOrderDateDesc(Integer userId);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT DISTINCT o FROM Order o " +
            "JOIN o.orderItems oi " +
            "WHERE oi.product.category.id = :categoryId")
    List<Order> findOrdersByProductCategory(@Param("categoryId") Integer categoryId);
}
