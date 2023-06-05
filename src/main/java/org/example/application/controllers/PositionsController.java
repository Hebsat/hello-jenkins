package org.example.application.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.example.application.api.request.PositionRequest;
import org.example.application.api.response.PositionResponse;
import org.example.application.api.response.Response;
import org.example.application.config.SpringfoxConfig;
import org.example.application.exceptions.EmptyFieldsException;
import org.example.application.exceptions.IncorrectDataException;
import org.example.application.exceptions.NoSuchEntityException;
import org.example.application.services.PositionsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
@Api(tags = {SpringfoxConfig.TAG_2})
public class PositionsController {

    private final PositionsService positionsService;

    @GetMapping("/{name}")
    @ApiOperation(value = "Method to get position by it's name")
    public Response<PositionResponse> getPositionByName(
            @ApiParam("position name") @PathVariable String name) throws NoSuchEntityException {

        return positionsService.getPosition(name);
    }

    @GetMapping
    @ApiOperation(value = "Method to get list of all positions")
    public Response<List<PositionResponse>> getAllPositions() {

        return positionsService.getPositions();
    }

    @PostMapping
    @ApiOperation(value = "Method to create new position")
    public Response<?> createPosition(
            @ApiParam("position name") @RequestBody PositionRequest positionRequest) throws EmptyFieldsException, IncorrectDataException {

        return positionsService.createPosition(positionRequest);
    }

    @PutMapping("/{name}")
    @ApiOperation(value = "Method to change position name")
    public Response<?> modifyPosition(
            @ApiParam("position old name") @PathVariable String name,
            @ApiParam("position new name") @RequestBody PositionRequest positionRequest) throws NoSuchEntityException, EmptyFieldsException {

        return positionsService.modifyPosition(name, positionRequest);
    }

    @DeleteMapping("/{name}")
    @ApiOperation(value = "Method to remove position by it's name")
    public Response<?> removePosition(
            @ApiParam("position name") @PathVariable String name) throws NoSuchEntityException {

        return positionsService.deletePosition(name);
    }
}
