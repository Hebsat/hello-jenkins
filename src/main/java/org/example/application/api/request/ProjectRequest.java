package org.example.application.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "request form for project")
public class ProjectRequest {

    @ApiModelProperty(value = "project name", example = "Some boring project")
    private String name;
}
