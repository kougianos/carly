package com.carly.model.collection;

import com.carly.enumeration.ERole;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data
@ToString(exclude = "id")
public class Role {
    @Id
    private String id;
    private ERole name;
}