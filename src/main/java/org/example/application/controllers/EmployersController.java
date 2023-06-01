package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Employee;
import org.example.application.services.EmployeesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployersController {

    private final EmployeesService employeesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Employee>> getEmployers() {

        return employeesService.getEmployees();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Employee> getEmployee(@PathVariable int id) {

        return employeesService.getEmployee(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> createEmployee(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        return employeesService.createEmployee(firstName, lastName);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> modifyEmployee(
            @PathVariable int id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        return employeesService.modifyEmployee(id, firstName, lastName);
    }

    @DeleteMapping("/{id}")
    public Response<?> removeEmployee(@PathVariable int id) {

        return employeesService.deletePerson(id);
    }

    @PutMapping("/position/{id}")
    public Response<?> assignEmployeeToPosition(
            @PathVariable int id,
            @RequestParam String position) {

        return employeesService.assignEmployeeToPosition(id, position);
    }

    @PutMapping("/project/{id}")
    public Response<?> assignEmployeeToProject(
            @PathVariable int id,
            @RequestParam String project) {

        return employeesService.assignEmployeeToProject(id, project);
    }

    @DeleteMapping("/project/{id}")
    public Response<?> removeEmployeeFromProject(
            @PathVariable int id,
            @RequestParam String project) {

        return employeesService.removeEmployeeFromProject(id, project);
    }
}
