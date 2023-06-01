package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.mappers.EmployersMapper;
import org.example.application.mappers.PositionsMapper;
import org.example.application.model.Position;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PositionsRepository {

    private final ConnectionService connectionService;
    private final PositionsMapper positionsMapper;
    private final EmployersMapper employersMapper;

    public List<Position> getAllPositions() {
        String sql = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, " +
                "p.id positionId, p.name positionName FROM positions p " +
                "LEFT JOIN employees e ON e.position_id = p.id ORDER BY p.id, e.id;";
        List<Position> positions = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Position position = null;
            while (resultSet.next()) {
                if (position == null) {
                    position = positionsMapper.mapToPositionFull(resultSet);
                }
                else if (position.getId() == resultSet.getInt("positionId")) {
                    position.getEmployees().add(employersMapper.mapToEmployeeSimple(resultSet));
                }
                else {
                    positions.add(position);
                    position = positionsMapper.mapToPositionFull(resultSet);
                }
            }
            if (position != null) {
                positions.add(position);
            }
            return positions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Position getPositionByName(String name) {
        String sql = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, " +
                "p.id positionId, p.name positionName FROM positions p " +
                "LEFT JOIN employees e ON e.position_id = p.id WHERE p.name = ?;";
        Position position = null;
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (position == null) {
                    position = positionsMapper.mapToPositionFull(resultSet);
                }
                else {
                    position.getEmployees().add(employersMapper.mapToEmployeeSimple(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return position;
    }

    public boolean addNewPosition(String name) {
        String sql = "INSERT INTO positions (name) VALUES (?)";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeName(String oldName, String newName) {
        String sql = "UPDATE  positions SET name = ? WHERE name = ?";
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

    public boolean deletePosition(String name) {
        String sql = "DELETE FROM positions WHERE name = ?";
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
