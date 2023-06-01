package org.example.application.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.config.SpringfoxConfig;
import org.example.application.model.Position;
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
    public Response<Position> getPositionByName(
            @ApiParam("position name") @PathVariable String name) {

        return positionsService.getPosition(name);
    }

    @GetMapping
    @ApiOperation(value = "Method to get list of all positions")
    public Response<List<Position>> getAllPositions() {

        return positionsService.getPositions();
    }

    @PostMapping
    @ApiOperation(value = "Method to create new position")
    public Response<?> createPosition(
            @ApiParam("position name") @RequestParam String name) {

        return positionsService.createPosition(name);
    }

    @PutMapping("/{name}")
    @ApiOperation(value = "Method to change position name")
    public Response<?> modifyPosition(
            @ApiParam("position old name") @PathVariable String name,
            @ApiParam("position new name") @RequestParam String newName) {

        return positionsService.modifyPosition(name, newName);
    }

    @DeleteMapping("/{name}")
    @ApiOperation(value = "Method to remove position by it's name")
    public Response<?> removePosition(
            @ApiParam("position name") @PathVariable String name) {

        return positionsService.deletePosition(name);
    }
}
