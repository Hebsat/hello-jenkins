package org.example.application.mappers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.response.EmployeeResponse;
import org.example.application.api.response.ProjectResponse;
import org.example.application.model.Employee;
import org.example.application.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsMapper {

    private final EmployeesMapper employeesMapper;

    public ProjectResponse projectToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .employees(getEmployeesResponses(project.getEmployees()))
                .build();
    }

    private List<EmployeeResponse> getEmployeesResponses(List<Employee> employees) {
        return employeesMapper.mapEmployees(employees);
    }
}
