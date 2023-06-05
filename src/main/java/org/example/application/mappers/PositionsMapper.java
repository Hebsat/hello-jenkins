package org.example.application.mappers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.response.EmployeeResponse;
import org.example.application.api.response.PositionResponse;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsMapper {

    private final EmployeesMapper employeesMapper;

    public PositionResponse positionToResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .employees(getEmployeesResponses(position.getEmployees()))
                .build();
    }

    private List<EmployeeResponse> getEmployeesResponses(List<Employee> employees) {
        return employeesMapper.mapEmployees(employees);
    }
}
