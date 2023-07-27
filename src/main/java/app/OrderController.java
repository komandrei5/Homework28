package app;

import com.google.gson.Gson;

import static spark.Spark.port;
import static spark.Spark.post;

public class OrderController {
        private static OrderRepository orderRepository = new OrderRepository();
        private static Gson gson = new Gson();

        public static void main(String[] args) {
            port(8080);

            // Endpoint to get a specific order by ID
            get("/order/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                Order order = orderRepository.getOrderById(id);
                if (order != null) {
                    return gson.toJson(order);
                } else {
                    res.status(404);
                    return "Order not found";
                }
            });
            // Endpoint to get all orders
            get("/orders", (req, res) -> gson.toJson(orderRepository.getAllOrders()));

            // Endpoint to add a new order
            post("/order", (req, res) -> {
                Order order = gson.fromJson(req.body(), Order.class);
                orderRepository.addOrder(order);
                res.status(201);
                return "Order added successfully";
            });
        }
    }

