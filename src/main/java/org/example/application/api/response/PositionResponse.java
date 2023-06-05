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
@ApiModel(description = "response form for employee's positions")
public class PositionResponse {

    @ApiModelProperty(value = "position's id number in DB", example = "2")
    private Integer id;
    @ApiModelProperty(value = "position's name", example = "Developer")
    private String name;
    @ApiModelProperty(value = "list of employees, holding this position", example = "[Kenny McCormick, Stan Marsh]")
    private List<EmployeeResponse> employees;
}
