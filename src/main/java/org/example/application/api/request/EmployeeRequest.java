package org.example.application.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "request form for employee")
public class EmployeeRequest {

    @ApiModelProperty(value = "employee's first name", example = "Kenny")
    private String firstName;
    @ApiModelProperty(value = "employee's last name", example = "McCormick")
    private String lastName;
}
