package org.example.application.mappers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.response.EmployeeResponse;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeesMapper {

    public EmployeeResponse employeeToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(this.getPositionName(employee.getPosition()))
                .projects(this.getProjectsNames(employee.getProjects())).build();
    }

    private String getPositionName(Position position) {
        return position == null ? null : position.getName();
    }

    private List<String> getProjectsNames(List<Project> projects) {
        return projects.stream().map(Project::getName).collect(Collectors.toList());
    }

    public List<EmployeeResponse> mapEmployees(List<Employee> employees) {
        return employees.stream().map(this::employeeToResponseSimple).collect(Collectors.toList());
    }

    private EmployeeResponse employeeToResponseSimple(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(this.getPositionName(employee.getPosition()))
                .build();
    }
}
