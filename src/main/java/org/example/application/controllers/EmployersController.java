package org.example.application.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.config.SpringfoxConfig;
import org.example.application.model.Employee;
import org.example.application.services.EmployeesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Api(tags = {SpringfoxConfig.TAG_1})
public class EmployersController {

    private final EmployeesService employeesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to get list of all employees")
    public Response<List<Employee>> getEmployers() {

        return employeesService.getEmployees();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to get employee by ID number")
    public Response<Employee> getEmployee(
            @ApiParam("employee ID number") @PathVariable int id) {

        return employeesService.getEmployee(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to create new employee")
    public Response<?> createEmployee(
            @ApiParam("employee first name") @RequestParam String firstName,
            @ApiParam("employee ID last name") @RequestParam String lastName) {

        return employeesService.createEmployee(firstName, lastName);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to change employee's first or last name")
    public Response<?> modifyEmployee(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("employee first name") @RequestParam(required = false) String firstName,
            @ApiParam("employee ID last name") @RequestParam(required = false) String lastName) {

        return employeesService.modifyEmployee(id, firstName, lastName);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method to remove employee by his ID number")
    public Response<?> removeEmployee(
            @ApiParam("employee ID number") @PathVariable int id) {

        return employeesService.deletePerson(id);
    }

    @PutMapping("/position/{id}")
    @ApiOperation(value = "Method to assign employee for some position by his ID number and position name")
    public Response<?> assignEmployeeToPosition(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("assigning position name") @RequestParam String position) {

        return employeesService.assignEmployeeToPosition(id, position);
    }

    @PutMapping("/project/{id}")
    @ApiOperation(value = "Method to assign employee for some project by his ID number and project name")
    public Response<?> assignEmployeeToProject(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("assigning project name") @RequestParam String project) {

        return employeesService.assignEmployeeToProject(id, project);
    }

    @DeleteMapping("/project/{id}")
    @ApiOperation(value = "Method to remove employee from project by his ID number and project name")
    public Response<?> removeEmployeeFromProject(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("removing project name") @RequestParam String project) {

        return employeesService.removeEmployeeFromProject(id, project);
    }
}
