package app;

import com.google.gson.Gson;

import java.util.List;

import static java.lang.reflect.Array.get;
import static spark.Spark.*;

public class OrderController {
    public static void main(String[] args) {
        final OrderRepository orderRepository = new OrderRepository() ;

            // http://localhost:4567/orders
            // Отримання всіх замовлень
        get("/orders", (request, response) -> {
            response.type("application/json");
            List<Order> allOrders = orderRepository.getAllOrders();
            return new Gson().toJson(allOrders);
        });

            // http://localhost:4567/orders/:id
            // Отримання конкретного замовлення за ID
            get("/orders/:id", (request, response) -> {
                response.type("application/json");
                String orderId = request.params(":id");
                Order order = orderRepository.getOrder(orderId);
                if (order != null) {
                    return new Gson().toJson(order);
                } else {
                    response.status(404); // Not Found
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Order not found"));
                }
            });

        // http://localhost:4567/orders
        // Додавання нового замовлення
        post("/orders", (request, response) -> {
            response.type("application/json");
            Order newOrder = new Gson().fromJson(request.body(), Order.class);
            orderRepository.addOrder(newOrder);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Order added successfully"));
        });
    }
}

