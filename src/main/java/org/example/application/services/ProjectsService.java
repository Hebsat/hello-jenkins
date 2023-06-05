package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.request.ProjectRequest;
import org.example.application.api.response.ProjectResponse;
import org.example.application.api.response.Response;
import org.example.application.exceptions.EmptyFieldsException;
import org.example.application.exceptions.IncorrectDataException;
import org.example.application.exceptions.NoSuchEntityException;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Project;
import org.example.application.repositories.ProjectsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final ProjectsMapper projectsMapper;

    public Response<List<ProjectResponse>> getProjects() {
        List<Project> projects = projectsRepository.findAll();
        return Response.<List<ProjectResponse>>builder()
                .count(projects.size())
                .data(getResponses(projects))
                .build();
    }

    private List<ProjectResponse> getResponses(List<Project> projects) {
        return projects.stream().map(projectsMapper::projectToResponse).collect(Collectors.toList());
    }

    public Response<ProjectResponse> getProject(String name) throws NoSuchEntityException {
        Project project = projectsRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException("project with name " + name + " not found"));
        return Response.<ProjectResponse>builder()
                .count(1)
                .data(projectsMapper.projectToResponse(project))
                .build();
    }

    public Response<?> createProject(ProjectRequest projectRequest) throws EmptyFieldsException, IncorrectDataException {
        validateProjectRequest(projectRequest);
        Project project = buildProject(projectRequest);
        return Response.builder()
                .success(projectsRepository.save(project))
                .build();
    }

    private void validateProjectRequest(ProjectRequest projectRequest) throws EmptyFieldsException {
        if (projectRequest.getName() == null) {
            throw new EmptyFieldsException("empty name field value");
        }
    }

    private Project buildProject(ProjectRequest projectRequest) {
        return Project.builder()
                .name(projectRequest.getName())
                .build();
    }

    public Response<?> modifyProject(String oldName, ProjectRequest projectRequest) throws EmptyFieldsException, NoSuchEntityException {
        validateProjectRequest(projectRequest);
        projectsRepository.findByName(oldName)
                .orElseThrow(() -> new NoSuchEntityException("project with name " + projectRequest.getName() + " not found"));
        return Response.builder()
                .success(projectsRepository.update(oldName, projectRequest.getName()))
                .build();
    }

    public Response<?> deleteProject(String name) throws NoSuchEntityException {
        Project project = projectsRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException("project with name " + name + " not found"));
        return Response.builder()
                .success(projectsRepository.delete(project))
                .build();
    }
}
