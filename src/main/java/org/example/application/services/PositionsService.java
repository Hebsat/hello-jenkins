package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.request.PositionRequest;
import org.example.application.api.response.PositionResponse;
import org.example.application.api.response.Response;
import org.example.application.exceptions.EmptyFieldsException;
import org.example.application.exceptions.IncorrectDataException;
import org.example.application.exceptions.NoSuchEntityException;
import org.example.application.mappers.PositionsMapper;
import org.example.application.model.Position;
import org.example.application.repositories.PositionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionsService {

    private final PositionsRepository positionsRepository;
    private final PositionsMapper positionsMapper;

    public Response<List<PositionResponse>> getPositions() {
        List<Position> positions = positionsRepository.findAll();
        return Response.<List<PositionResponse>>builder()
                .count(positions.size())
                .data(getResponses(positions))
                .build();
    }

    private List<PositionResponse> getResponses(List<Position> positions) {
        return positions.stream().map(positionsMapper::positionToResponse).collect(Collectors.toList());
    }

    public Response<PositionResponse> getPosition(String name) throws NoSuchEntityException {
        Position position = positionsRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException("position with name " + name + " not found"));
        return Response.<PositionResponse>builder()
                .count(1)
                .data(positionsMapper.positionToResponse(position))
                .build();
    }

    public Response<?> createPosition(PositionRequest positionRequest) throws EmptyFieldsException, IncorrectDataException {
        validatePositionRequest(positionRequest);
        Position position = buildPosition(positionRequest);
        return Response.builder()
                .success(positionsRepository.save(position))
                .build();
    }

    private void validatePositionRequest(PositionRequest positionRequest) throws EmptyFieldsException {
        if (positionRequest.getName() == null) {
            throw new EmptyFieldsException("empty name field value");
        }
    }

    private Position buildPosition(PositionRequest positionRequest) {
        return Position.builder()
                .name(positionRequest.getName())
                .build();
    }

    public Response<?> modifyPosition(String name, PositionRequest positionRequest) throws EmptyFieldsException, NoSuchEntityException {
        validatePositionRequest(positionRequest);
        positionsRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException("position with name " + name + " not found"));
        return Response.builder()
                .success(positionsRepository.update(name, positionRequest.getName()))
                .build();
    }

    public Response<?> deletePosition(String name) throws NoSuchEntityException {
        Position position = positionsRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException("position with name " + name + " not found"));
        return Response.builder()
                .success(positionsRepository.delete(position))
                .build();
    }
}
