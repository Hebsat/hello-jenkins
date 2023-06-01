package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Project;
import org.example.application.services.ProjectsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @GetMapping("/{name}")
    public Response<Project> getProjectByName(@PathVariable String name) {

        return projectsService.getProject(name);
    }

    @GetMapping
    public Response<List<Project>> getAllProjects() {

        return projectsService.getProjects();
    }

    @PostMapping
    public Response<?> createProject(@RequestParam String name) {

        return projectsService.createProject(name);
    }

    @PutMapping("/{name}")
    public Response<?> modifyProject(
            @PathVariable String name,
            @RequestParam(required = false) String newName) {

        return projectsService.modifyProject(name, newName);
    }

    @DeleteMapping("/{name}")
    public Response<?> removeProject(@PathVariable String name) {

        return projectsService.deleteProject(name);
    }
}
