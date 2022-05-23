package com.carly.model.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@ToString(exclude = {"id"})
@Document(collection = "cars")
public class Car {
    @Id
    private String id;
    private String model;
    private String year;
    private String mileage;
    private String ownerId;
}