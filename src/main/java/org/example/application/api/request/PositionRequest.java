package org.example.application.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "request form for position")
public class PositionRequest {

    @ApiModelProperty(value = "position name", example = "Tester")
    private String name;
}
