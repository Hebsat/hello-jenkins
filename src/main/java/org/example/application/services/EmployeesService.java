package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.request.EmployeeRequest;
import org.example.application.api.request.PositionRequest;
import org.example.application.api.request.ProjectRequest;
import org.example.application.api.response.EmployeeResponse;
import org.example.application.api.response.Response;
import org.example.application.exceptions.EmptyFieldsException;
import org.example.application.exceptions.NoSuchEntityException;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.example.application.repositories.EmployersRepository;
import org.example.application.repositories.PositionsRepository;
import org.example.application.repositories.ProjectsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployersRepository employersRepository;
    private final PositionsRepository positionsRepository;
    private final ProjectsRepository projectsRepository;
    private final EmployeesMapper employeesMapper;

    public Response<List<EmployeeResponse>> getEmployees() {
        List<Employee> employees = employersRepository.findAll();
        return Response.<List<EmployeeResponse>>builder()
                .count(employees.size())
                .data(getResponses(employees))
                .build();
    }

    private List<EmployeeResponse> getResponses(List<Employee> employees) {
        return employees.stream().map(employeesMapper::employeeToResponse).collect(Collectors.toList());
    }

    public Response<EmployeeResponse> getEmployee(int id) throws NoSuchEntityException {
        Employee employee = employersRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("employee with id " + id + " not found"));
        return Response.<EmployeeResponse>builder()
                .count(1)
                .data(employeesMapper.employeeToResponse(employee))
                .build();
    }


    public Response<?> createEmployee(EmployeeRequest employeeRequest) throws EmptyFieldsException {
        validateEmployeeRequest(employeeRequest);
        Employee employee = buildEmployee(employeeRequest);
        return Response.builder()
                .success(employersRepository.save(employee))
                .build();
    }

    private void validateEmployeeRequest(EmployeeRequest employeeRequest) throws EmptyFieldsException {
        if (employeeRequest.getFirstName() == null && employeeRequest.getLastName() == null) {
            throw new EmptyFieldsException("empty first name and last name values");
        }
    }

    private Employee buildEmployee(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .build();
    }

    public Response<?> modifyEmployee(int id, EmployeeRequest employeeRequest) throws EmptyFieldsException, NoSuchEntityException {
        validateEmployeeRequest(employeeRequest);
        employersRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("employee with id " + id + " not found"));
        return Response.builder()
                .success(employersRepository.update(id, employeeRequest.getFirstName(), employeeRequest.getLastName(), null, null))
                .build();
    }

    public Response<?> deletePerson(int id) throws NoSuchEntityException {
        Employee employee = employersRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("employee with id " + id + " not found"));
        return Response.builder()
                .success(employersRepository.delete(employee))
                .build();
    }

    public Response<?> assignEmployeeToPosition(int id, PositionRequest positionRequest) throws NoSuchEntityException {
        employersRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("employee with id " + id + " not found"));
        Position position = positionsRepository.findByName(positionRequest.getName())
                .orElseThrow(() -> new NoSuchEntityException("position with name " + positionRequest.getName() + " not found"));
        return Response.builder()
                .success(employersRepository.update(id,null, null, position, null))
                .build();
    }

    public Response<?> assignEmployeeToProject(int id, ProjectRequest projectRequest) throws NoSuchEntityException {
        Employee employee = employersRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("employee with id " + id + " not found"));
        Project project = projectsRepository.findByName(projectRequest.getName())
                .orElseThrow(() -> new NoSuchEntityException("project with name " + projectRequest.getName() + " not found"));
        List<Project> projects = employee.getProjects();
        projects.add(project);
        return Response.builder()
                .success(employersRepository.update(id,null, null, null, projects))
                .build();
    }

    public Response<?> removeEmployeeFromProject(int id, ProjectRequest projectRequest) throws NoSuchEntityException {
        Employee employee = employersRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("employee with id " + id + " not found"));
        Project project = projectsRepository.findByName(projectRequest.getName())
                .orElseThrow(() -> new NoSuchEntityException("project with name " + projectRequest.getName() + " not found"));
        List<Project> projects = employee.getProjects();
        projects.remove(project);
        return Response.builder()
                .success(employersRepository.deleteAssigningToProject(employee, project))
                .build();
    }
}
