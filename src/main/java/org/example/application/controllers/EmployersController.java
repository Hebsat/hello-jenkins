package org.example.application.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.example.application.api.request.EmployeeRequest;
import org.example.application.api.request.PositionRequest;
import org.example.application.api.request.ProjectRequest;
import org.example.application.api.response.EmployeeResponse;
import org.example.application.api.response.Response;
import org.example.application.config.SpringfoxConfig;
import org.example.application.exceptions.EmptyFieldsException;
import org.example.application.exceptions.NoSuchEntityException;
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
    public Response<List<EmployeeResponse>> getEmployers() {

        return employeesService.getEmployees();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to get employee by ID number")
    public Response<EmployeeResponse> getEmployee(
            @ApiParam("employee ID number") @PathVariable int id) throws NoSuchEntityException {

        return employeesService.getEmployee(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Method to create new employee")
    public Response<?> createEmployee(
            @ApiParam("request for first and last names, each of them is optional")
            @RequestBody EmployeeRequest employeeRequest) throws EmptyFieldsException {

        return employeesService.createEmployee(employeeRequest);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to change employee's first or last name")
    public Response<?> modifyEmployee(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("request for first and last names, each of them is optional")
            @RequestBody EmployeeRequest employeeRequest) throws NoSuchEntityException, EmptyFieldsException {

        return employeesService.modifyEmployee(id, employeeRequest);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method to remove employee by his ID number")
    public Response<?> removeEmployee(
            @ApiParam("employee ID number") @PathVariable int id) throws NoSuchEntityException {

        return employeesService.deletePerson(id);
    }

    @PutMapping({"/{id}/position"})
    @ApiOperation("Method to assign employee for some position by his ID number and position name")
    public Response<?> assignEmployeeToPosition(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("assigning position name") @RequestBody PositionRequest positionRequest) throws NoSuchEntityException {

        return employeesService.assignEmployeeToPosition(id, positionRequest);
    }

    @PutMapping("/{id}/project")
    @ApiOperation(value = "Method to assign employee for some project by his ID number and project name")
    public Response<?> assignEmployeeToProject(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("assigning project name") @RequestBody ProjectRequest projectRequest) throws NoSuchEntityException {

        return employeesService.assignEmployeeToProject(id, projectRequest);
    }

    @DeleteMapping("/{id}/project")
    @ApiOperation(value = "Method to remove employee from project by his ID number and project name")
    public Response<?> removeEmployeeFromProject(
            @ApiParam("employee ID number") @PathVariable int id,
            @ApiParam("removing project name") @RequestBody ProjectRequest projectRequest) throws NoSuchEntityException {

        return employeesService.removeEmployeeFromProject(id, projectRequest);
    }
}
