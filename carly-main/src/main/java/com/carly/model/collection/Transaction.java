package com.carly.model.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(exclude = {"id"})
@Document(collection = "transactions")
public class Transaction {
	@Id
	private String id;
	private String buyerId;
	private String sellerId;
	private String carId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean isSuccessful;
}
