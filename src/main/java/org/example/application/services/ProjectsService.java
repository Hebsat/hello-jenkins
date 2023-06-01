package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Project;
import org.example.application.repositories.ProjectsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    private final ProjectsRepository projectsRepository;

    public Response<List<Project>> getProjects() {
        List<Project> positions = projectsRepository.getAllProjects();
        return Response.<List<Project>>builder()
                .count(positions.size())
                .data(positions)
                .build();
    }

    public Response<Project> getProject(String name) {
        Project project = projectsRepository.getProjectByName(name);
        if (project == null) {
            return Response.<Project>builder()
                    .success(false)
                    .build();
        }
        return Response.<Project>builder()
                .count(1)
                .data(project)
                .build();
    }

    public Response<?> createProject(String name) {
        return Response.builder()
                .success(projectsRepository.addNewProject(name))
                .build();
    }

    public Response<?> modifyProject(String oldName, String newName) {
        return Response.builder()
                .success(projectsRepository.changeProjectName(oldName, newName))
                .build();
    }

    public Response<?> deleteProject(String name) {
        return Response.builder()
                .success(projectsRepository.deleteProject(name))
                .build();
    }
}
