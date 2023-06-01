package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployersRepository employersRepository;

    public Response<List<Employee>> getEmployees() {
        List<Employee> employees = employersRepository.getAllEmployers();
        return Response.<List<Employee>>builder()
                .count(employees.size())
                .data(employees)
                .build();
    }

    public Response<Employee> getEmployee(int id) {
        Employee employee = employersRepository.getEmployeeById(id);
        if (employee == null) {
            return Response.<Employee>builder()
                    .success(false)
                    .build();
        }
        return Response.<Employee>builder()
                .count(1)
                .data(employee)
                .build();
    }

    public Response<?> createEmployee(String firstName, String lastName) {
        return Response.builder()
                .success(employersRepository.addNewEmployee(firstName, lastName))
                .build();
    }

    public Response<?> modifyEmployee(int id, String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return Response.builder()
                    .success(employersRepository.changeFullName(id, firstName, lastName))
                    .build();
        } else if (firstName != null) {
            return Response.builder()
                    .success(employersRepository.changeFirstName(id, firstName))
                    .build();
        } else if (lastName != null) {
            return Response.builder()
                    .success(employersRepository.changeLastName(id, lastName))
                    .build();
        } else {
            return Response.builder()
                    .success(false)
                    .build();
        }
    }

    public Response<?> deletePerson(int id) {
        return Response.builder()
                .success(employersRepository.deleteEmployee(id))
                .build();
    }

    public Response<?> assignEmployeeToPosition(int id, String position) {
        return Response.builder()
                .success(employersRepository.assignToPosition(id, position))
                .build();
    }

    public Response<?> assignEmployeeToProject(int id, String project) {
        return Response.builder()
                .success(employersRepository.assignToProject(id, project))
                .build();
    }

    public Response<?> removeEmployeeFromProject(int id, String project) {
        return Response.builder()
                .success(employersRepository.removeFromProject(id, project))
                .build();
    }
}
