package com.ems.restapidto.controller;

import com.ems.restapidto.dto.EmployeeDto;
import com.ems.restapidto.exception.ErrorDetails;
import com.ems.restapidto.exception.ResourceNotFoundException;
import com.ems.restapidto.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
@Tag(
        name = "API for EMPLOYEE resource",
        description = "CRUD REST API- PUT Employee, GET Employee , POST Employee , DELETE Employee, GET All Employees "
)
@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //rest api to create an employee
    @Operation(
            summary = "CREATE Employee Rest API",
            description = "create employee rest api is used to save the employee in  a database using post request" +
                    "and sending the employee details in response body"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 created"
    )
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        EmployeeDto employeeDto1=employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET Employee BY ID rest api",
            description = "GET Employee rest api is used to fetch the employee data from the database based on the id given"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 success"
    )
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id){
        EmployeeDto employeeDto=employeeService.getEmployee(id);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @Operation(
            summary = "PUT Employee Rest api",
            description = "Put employee rest api is used to update the employee information that is already present in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 success"
    )
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,@RequestBody @Valid EmployeeDto employeeDto){
      employeeDto.setId(id);
      EmployeeDto employeeDto1=employeeService.updateEmployee(employeeDto);
      return new ResponseEntity<>(employeeDto1,HttpStatus.OK);
    }

    @Operation(
       summary = "GET All employees rest api",
       description = "Get all employees is used to fetch all the employee data available in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 success"
    )
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(){
        List<EmployeeDto> employees= employeeService.getEmployees();
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE Employee Rest api",
            description = "DELETE employee rest api is used to delete the employee data from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 success"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
      employeeService.deleteEmployee(id);
      return new ResponseEntity<>("deleted succesfully",HttpStatus.OK);
    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                                        WebRequest webrequest){
//        ErrorDetails errorDetails=new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webrequest.getDescription(false),
//                "USER_NOT_FOUND"
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }



}
