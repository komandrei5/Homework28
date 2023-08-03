package app;

import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Order {
    private UUID id;
    private Timestamp createAt;
    private Timestamp updateAt;
    private double cost;
    private List<Product> products = new ArrayList<>();

}
