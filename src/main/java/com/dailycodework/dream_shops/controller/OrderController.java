package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.OrderDto;
import com.dailycodework.dream_shops.exceptions.ResourcesNotFoundException;
import com.dailycodework.dream_shops.model.Order;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order Success!", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error occurred", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Success", order));
        } catch (ResourcesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Success", orders));
        } catch (ResourcesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }
}
