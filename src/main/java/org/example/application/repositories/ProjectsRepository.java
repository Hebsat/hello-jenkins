package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.mappers.EmployersMapper;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Project;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsRepository {

    private final ProjectsMapper projectsMapper;
    private final ConnectionService connectionService;
    private final EmployersMapper employersMapper;

    public List<Project> getAllProjects() {
        String sql = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, " +
                "p.id positionId, p.name positionName, j.id projectId, j.name projectName " +
                "FROM projects j " +
                "LEFT JOIN employees_2_projects t ON t.project_id = j.id " +
                "LEFT JOIN employees e ON t.employee_id = e.id " +
                "LEFT JOIN positions p ON e.position_id = p.id " +
                "ORDER BY j.id, e.id";
        List<Project> projects = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Project project = null;
            while (resultSet.next()) {
                if (project == null) {
                    project = projectsMapper.mapToProjectFull(resultSet);
                }
                else if (project.getId() == resultSet.getInt("projectId")) {
                    project.getEmployees().add(employersMapper.mapToEmployeeSimple(resultSet));
                }
                else {
                    projects.add(project);
                    project = projectsMapper.mapToProjectFull(resultSet);
                }
            }
            if (project != null) {
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Project getProjectByName(String name) {
        String sql = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, " +
                "p.id positionId, p.name positionName, j.id projectId, j.name projectName " +
                "FROM projects j " +
                "LEFT JOIN employees_2_projects t ON t.project_id = j.id " +
                "LEFT JOIN employees e ON t.employee_id = e.id " +
                "LEFT JOIN positions p ON e.position_id = p.id " +
                " WHERE j.name = ?;";
        Project project = null;
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (project == null) {
                    project = projectsMapper.mapToProjectFull(resultSet);
                }
                else {
                    project.getEmployees().add(employersMapper.mapToEmployeeSimple(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    public boolean addNewProject(String name) {
        String sql = "INSERT INTO projects (name) VALUES (?)";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeProjectName(String oldName, String newName) {
        String sql = "UPDATE  projects SET name = ? WHERE name = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProject(String name) {
        String sql = "DELETE FROM projects WHERE name = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
