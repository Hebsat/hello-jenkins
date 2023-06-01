package org.example.application.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.config.SpringfoxConfig;
import org.example.application.model.Project;
import org.example.application.services.ProjectsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Api(tags = {SpringfoxConfig.TAG_3})
public class ProjectsController {

    private final ProjectsService projectsService;

    @GetMapping("/{name}")
    @ApiOperation(value = "Method to get project by it's name")
    public Response<Project> getProjectByName(
            @ApiParam("project name") @PathVariable String name) {

        return projectsService.getProject(name);
    }

    @GetMapping
    @ApiOperation(value = "Method to get list of all projects")
    public Response<List<Project>> getAllProjects() {

        return projectsService.getProjects();
    }

    @PostMapping
    @ApiOperation(value = "Method to create new project")
    public Response<?> createProject(
            @ApiParam("project name") @RequestParam String name) {

        return projectsService.createProject(name);
    }

    @PutMapping("/{name}")
    @ApiOperation(value = "Method to change project name")
    public Response<?> modifyProject(
            @ApiParam("project old name") @PathVariable String name,
            @ApiParam("project new name") @RequestParam(required = false) String newName) {

        return projectsService.modifyProject(name, newName);
    }

    @DeleteMapping("/{name}")
    @ApiOperation(value = "Method to remove project by it's name")
    public Response<?> removeProject(
            @ApiParam("project name") @PathVariable String name) {

        return projectsService.deleteProject(name);
    }
}
