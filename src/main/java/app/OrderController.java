package app;

import com.google.gson.Gson;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class OrderController {

    private static List<Order> orderList = new ArrayList<>();
    public static void main(String[] args) {

            // http://localhost:4567/orders
            // Get all orders
        get("/orders", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(orderList);
        });

            // http://localhost:4567/orders/:uuid
            // Get order by id
            get("/orders/:uuid", (request, response) -> {
                response.type("application/json");
                UUID uuid = UUID.fromString(request.params(":uuid"));

                return new Gson().toJsonTree(orderList.stream()
                        .filter(o -> o.getId().equals(uuid))
                        .findFirst().get());
            });

        // http://localhost:4567/orders
        // create order
        post("/orders", (request, response) -> {
            response.type("application/json");
            Product product = new Gson().fromJson(request.body(), Product.class);
            product.setId(UUID.randomUUID());

           Order order = new Order();
           order.setId(UUID.randomUUID());
           order.setCreateAt(new Timestamp(System.currentTimeMillis()));
           order.setUpdateAt(new Timestamp(System.currentTimeMillis()));
           order.getProducts().add(product);

           orderList.add(order);
           return new Gson().toJsonTree(order);
              });

        // http://localhost:4567/orders
        // Update order
        put("/orders/:uuid", (request, response) -> {
            response.type("application/json");

            response.type("application/json");
            UUID uuid = UUID.fromString(request.params("id"));

            Order order = orderList.stream()
                    .filter(o -> o.getId().equals(uuid))
                    .findFirst().get();

            order.setUpdateAt(new Timestamp(System.currentTimeMillis()));

            Product product = new Gson().fromJson(request.body(), Product.class);
            product.setId(UUID.randomUUID());
            order.getProducts().add(product);
            order.setCost(order.getProducts().stream().mapToDouble(Product::getCost).sum());

            return new Gson().toJsonTree(order);
        });
    }
}

