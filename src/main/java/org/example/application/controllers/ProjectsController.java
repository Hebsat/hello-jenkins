package org.example.application.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.example.application.api.request.ProjectRequest;
import org.example.application.api.response.ProjectResponse;
import org.example.application.api.response.Response;
import org.example.application.config.SpringfoxConfig;
import org.example.application.exceptions.EmptyFieldsException;
import org.example.application.exceptions.IncorrectDataException;
import org.example.application.exceptions.NoSuchEntityException;
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
    public Response<ProjectResponse> getProjectByName(
            @ApiParam("project name") @PathVariable String name) throws NoSuchEntityException {

        return projectsService.getProject(name);
    }

    @GetMapping
    @ApiOperation(value = "Method to get list of all projects")
    public Response<List<ProjectResponse>> getAllProjects() {

        return projectsService.getProjects();
    }

    @PostMapping
    @ApiOperation(value = "Method to create new project")
    public Response<?> createProject(
            @ApiParam("project name") @RequestBody ProjectRequest projectRequest) throws EmptyFieldsException, IncorrectDataException {

        return projectsService.createProject(projectRequest);
    }

    @PutMapping("/{name}")
    @ApiOperation(value = "Method to change project name")
    public Response<?> modifyProject(
            @ApiParam("project old name") @PathVariable String name,
            @ApiParam("project new name") @RequestBody ProjectRequest projectRequest) throws NoSuchEntityException, EmptyFieldsException {

        return projectsService.modifyProject(name, projectRequest);
    }

    @DeleteMapping("/{name}")
    @ApiOperation(value = "Method to remove project by it's name")
    public Response<?> removeProject(
            @ApiParam("project name") @PathVariable String name) throws NoSuchEntityException {

        return projectsService.deleteProject(name);
    }
}
