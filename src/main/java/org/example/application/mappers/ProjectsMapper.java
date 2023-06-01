package org.example.application.mappers;

import lombok.RequiredArgsConstructor;
import org.example.application.model.Employee;
import org.example.application.model.Project;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsMapper {

    private final EmployersMapper employersMapper;

    public Project mapToProjectFull(ResultSet resultSet) {
        try {
            return Project.builder()
                    .id(resultSet.getInt("projectId"))
                    .name(resultSet.getString("projectName"))
                    .employees(createEmployeesList(resultSet))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
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
