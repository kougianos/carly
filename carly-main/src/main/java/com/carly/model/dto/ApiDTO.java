package com.carly.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiDTO {
    @NotNull
    @NotEmpty
    private String ownerId;
    private String ownerUsername;

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