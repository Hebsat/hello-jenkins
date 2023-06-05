package org.example.application.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
@ApiModel(description = "response form for employees")
public class EmployeeResponse {

    @ApiModelProperty(value = "employee's id number in DB", example = "2")
    private Integer id;
    @ApiModelProperty(value = "employee's first name", example = "Kenny")
    private String firstName;
    @ApiModelProperty(value = "employee's last name", example = "McCormick")
    private String lastName;
    @ApiModelProperty(value = "employee's position in company", example = "Developer")
    private String position;
    @ApiModelProperty(value = "projects for which the employee is assigned", example = "[First project, Second project]")
    private List<String> projects;
}
