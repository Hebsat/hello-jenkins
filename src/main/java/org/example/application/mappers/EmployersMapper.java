package org.example.application.mappers;

import lombok.RequiredArgsConstructor;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployersMapper {

    public Employee mapToEmployee(ResultSet resultSet) {
        try {
            return Employee.builder()
                    .id(resultSet.getInt("employeeId"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .position(mapToPositionSimple(resultSet))
                    .projects(createProjectsList(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Position mapToPositionSimple(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("positionId");
            return id == 0 ?
                    null :
                    Position.builder()
                            .id(id)
                            .name(resultSet.getString("positionName"))
                            .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Project> createProjectsList(ResultSet resultSet) {
        Project project = mapToProjectSimple(resultSet);
        List<Project> projects = new ArrayList<>();
        if (project.getId() != 0) {
            projects.add(project);
        }
        return projects;
    }

    public Project mapToProjectSimple(ResultSet resultSet) {
        try {
            return Project.builder()
                    .id(resultSet.getInt("projectId"))
                    .name(resultSet.getString("projectName"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Employee mapToEmployeeSimple(ResultSet resultSet) {
        try {
            return Employee.builder()
                    .id(resultSet.getInt("employeeId"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .position(mapToPositionSimple(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
