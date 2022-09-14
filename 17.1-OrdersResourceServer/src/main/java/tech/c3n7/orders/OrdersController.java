package tech.c3n7.orders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class OrdersController {
    @GetMapping("/orders")
    public List<OrderRest> getOrders() {
        OrderRest order1 = new OrderRest(
                UUID.randomUUID().toString(),
                "user-id-1",
                "product-id-1",
                1,
                OrderStatus.NEW
        );

        OrderRest order2 = new OrderRest(
                UUID.randomUUID().toString(),
                "user-id-2",
                "product-id-2",
                21,
                OrderStatus.NEW
        );

        return Arrays.asList(order1, order2);
    }
}
