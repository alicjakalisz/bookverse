package com.bookverse.bookverse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//to fetch Json response from remote API


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Data transfer object representing a open library response")// response json containts dozen fields, we ignore those that we
//are not interested in
public class OpenLibraryResponseDto {

    private String title;

    @JsonProperty("publish_date") // original name of the field in JSon response is publish_date so we need to map to publishDate
    private String publishDate;

    @JsonProperty("covers")
    private List<Integer> covers;

    @JsonProperty("by_statement") // original name of the field in JSon response is by_statement so we need to map to description
    private String description;
}
