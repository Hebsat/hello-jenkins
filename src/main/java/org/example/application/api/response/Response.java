package org.example.application.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
@ApiModel(description = "universal response form")
public class Response<T> {

    @ApiModelProperty(value = "count of objects in data field", example = "4")
    private Integer count;
    @ApiModelProperty(value = "field with the main data", example = "object or object's collection requested type")
    private T data;
    @ApiModelProperty(value = "result of the last command", example = "true or false")
    private Boolean success;
    @ApiModelProperty(value = "some message after the last command", example = "id didn't find")
    private String message;
}
