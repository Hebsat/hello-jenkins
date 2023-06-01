package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Position;
import org.example.application.services.PositionsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionsController {

    private final PositionsService positionsService;

    @GetMapping("/{name}")
    public Response<Position> getPositionByName(@PathVariable String name) {

        return positionsService.getPosition(name);
    }

    @GetMapping
    public Response<List<Position>> getAllPositions() {

        return positionsService.getPositions();
    }

    @PostMapping
    public Response<?> createPosition(@RequestParam String name) {

        return positionsService.createPosition(name);
    }

    @PutMapping("/{name}")
    public Response<?> modifyPosition(
            @PathVariable String name,
            @RequestParam String newName) {

        return positionsService.modifyPosition(name, newName);
    }

    @DeleteMapping("/{name}")
    public Response<?> removePosition(@PathVariable String name) {

        return positionsService.deletePosition(name);
    }
}
