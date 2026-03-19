package com.example.Saleswebsite.service;

import com.example.Saleswebsite.entity.CartItem;
import com.example.Saleswebsite.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@SessionScope
public class CartService {
    private Map<Integer, CartItem> map = new LinkedHashMap<>();

    public void add(Product product, int qty) {
        CartItem item = map.get(product.getId());
        if (item == null) {
            item = new CartItem(product.getId(), product.getName(),
                    product.getImageUrl(), product.getPrice(), qty);
            map.put(product.getId(), item);
        } else {
            item.setQuantity(item.getQuantity() + qty);
        }
    }

    public void update(Integer id, int qty) {
        CartItem item = map.get(id);
        if (item != null) item.setQuantity(qty);
    }

    public void remove(Integer id) {
        map.remove(id);
    }

    public void clear() {
        map.clear();
    }

    public Collection<CartItem> getItems() {
        return map.values();
    }

    public double getTotal() {
        return map.values().stream().mapToDouble(item -> item.getSubtotal()).sum();
    }
}
