package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.mappers.EmployersMapper;
import org.example.application.model.Employee;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployersRepository {

    private final ConnectionService connectionService;
    private final EmployersMapper employersMapper;

    public List<Employee> getAllEmployers() {
        String sql = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, " +
                "p.id positionId, p.name positionName, j.id projectId, j.name projectName " +
                "FROM employees e " +
                "LEFT JOIN positions p ON e.position_id = p.id " +
                "LEFT JOIN employees_2_projects t ON t.employee_id = e.id " +
                "LEFT JOIN projects j ON t.project_id = j.id " +
                "ORDER BY e.id, j.id";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Employee employee = null;
            while (resultSet.next()) {
                if (employee == null) {
                    employee = employersMapper.mapToEmployee(resultSet);
                }
                else if (employee.getId() == resultSet.getInt("employeeId")) {
                    employee.getProjects().add(employersMapper.mapToProjectSimple(resultSet));
                }
                else {
                    employees.add(employee);
                    employee = employersMapper.mapToEmployee(resultSet);
                }
            }
            if (employee != null) {
                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, " +
                "p.id positionId, p.name positionName,j.id projectId, j.name projectName " +
                "FROM employees e " +
                "LEFT JOIN positions p ON e.position_id = p.id " +
                "LEFT JOIN employees_2_projects t ON t.employee_id = e.id " +
                "LEFT JOIN projects j ON t.project_id = j.id " +
                "WHERE e.id = ?;";
        Employee employee = null;
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (employee == null) {
                    employee = employersMapper.mapToEmployee(resultSet);
                }
                else {
                    employee.getProjects().add(employersMapper.mapToProjectSimple(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    public boolean addNewEmployee(String firstName, String lastName) {
        String sql = "INSERT INTO employees (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeFirstName(int id, String firstName) {
        String sql = "UPDATE  employees SET first_name = ? WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeLastName(int id, String lastName) {
        String sql = "UPDATE  employees SET last_name = ? WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastName);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeFullName(int id, String firstName, String lastName) {
        String sql = "UPDATE  employees SET first_name = ?, last_name = ? WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignToPosition(int employeeId, String positionName) {
        String sql = "UPDATE  employees SET position_id = (SELECT id FROM positions WHERE name = ?) WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, positionName);
            statement.setInt(2, employeeId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignToProject(int employeeId, String projectName) {
        String sql = "INSERT INTO employees_2_projects (employee_id, project_id) VALUES (" +
                "(SELECT id FROM employees WHERE id = ?), (SELECT id FROM projects WHERE name = ?))";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.setString(2, projectName);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromProject(int employeeId, String projectName) {
        String sql = "DELETE FROM employees_2_projects WHERE " +
                "employee_id = ? AND project_id = (SELECT id FROM projects WHERE name = ?)";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.setString(2, projectName);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
