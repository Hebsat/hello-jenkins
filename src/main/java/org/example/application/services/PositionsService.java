package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Position;
import org.example.application.repositories.PositionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsService {

    private final PositionsRepository positionsRepository;

    public Response<List<Position>> getPositions() {
        List<Position> positions = positionsRepository.getAllPositions();
        return Response.<List<Position>>builder()
                .count(positions.size())
                .data(positions)
                .build();
    }

    public Response<Position> getPosition(String name) {
        Position position = positionsRepository.getPositionByName(name);
        if (position == null) {
            return Response.<Position>builder()
                    .success(false)
                    .build();
        }
        return Response.<Position>builder()
                .count(1)
                .data(position)
                .build();
    }

    public Response<?> createPosition(String name) {
        return Response.builder()
                .success(positionsRepository.addNewPosition(name))
                .build();
    }

    public Response<?> modifyPosition(String oldName, String newName) {
        return Response.builder()
                .success(positionsRepository.changeName(oldName, newName))
                .build();
    }

    public Response<?> deletePosition(String name) {
        return Response.builder()
                .success(positionsRepository.deletePosition(name))
                .build();
    }
}
