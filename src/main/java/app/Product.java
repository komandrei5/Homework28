package app;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
public class Product {
    private UUID id;
    private String name;
    private double cost;
}
