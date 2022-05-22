package com.carly.model.collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@Document(collection = "apis")
public class Api {
    @Id
    private String id;

    @NotNull
    @NotEmpty
    private String ownerId;
    private List<String> collaboratorsIds;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime creationDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime modificationDate;

    private String apiName;
    private String apiDescription;
    private String apiUrl;
    private List<JsonNode> projectModels;
}