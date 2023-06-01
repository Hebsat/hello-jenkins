package org.example.application.mappers;

import lombok.RequiredArgsConstructor;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsMapper {

    private final EmployersMapper employersMapper;

    public Position mapToPositionFull(ResultSet resultSet) {
        try {
            return Position.builder()
                    .id(resultSet.getInt("positionId"))
                    .name(resultSet.getString("positionName"))
                    .employees(createEmployeesList(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Employee> createEmployeesList(ResultSet resultSet) {
        Employee employee = employersMapper.mapToEmployeeSimple(resultSet);
        List<Employee> employees = new ArrayList<>();
        if (employee.getId() != 0) {
            employees.add(employee);
        }
        return employees;
    }
}
