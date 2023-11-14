package com.ems.restapidto.service;

import com.ems.restapidto.dto.EmployeeDto;
import com.ems.restapidto.entity.Employee;
import com.ems.restapidto.exception.EmailAlreadyExistException;
import com.ems.restapidto.exception.ResourceNotFoundException;
import com.ems.restapidto.mapper.EmployeeMapper;
import com.ems.restapidto.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;


    public EmployeeDto createEmployee(EmployeeDto employeeDto){
        // first check if this email already exist or not
        Optional<Employee> existingEmployee= employeeRepository.findByEmail(employeeDto.getEmail());
        if(existingEmployee.isPresent()){
            throw new EmailAlreadyExistException("This email already exist in the database");
        }

        // convert employeeDto into employee JPA entity
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee= employeeRepository.save(employee);

        // convert employee into employeeDto and then return it to the client
        EmployeeDto savedEmployeeDto= EmployeeMapper.mapToEmployeeDto(savedEmployee);
        return savedEmployeeDto;

    }

    public EmployeeDto getEmployee(Long id){
        // findById returns an optional -> get() method converts optional into employee object
//        Employee employee=employeeRepository.findById(id).get();
        Employee employee=employeeRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Employee","Id",id)
        );
        EmployeeDto employeeDto=EmployeeMapper.mapToEmployeeDto(employee);
        return employeeDto;
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto){

//        Employee existingEmployee= employeeRepository.findById(employeeDto.getId()).get();
        Employee existingEmployee=employeeRepository.findById(employeeDto.getId()).orElseThrow(
                ()->new ResourceNotFoundException("Employee","Id",employeeDto.getId())
        );
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setEmail(employeeDto.getEmail());

       Employee updatedEmployee= employeeRepository.save(existingEmployee);
       EmployeeDto updatedEmployeeDto=EmployeeMapper.mapToEmployeeDto(updatedEmployee);
       return updatedEmployeeDto;
    }

    public List<EmployeeDto> getEmployees() {
        List<Employee> employees=employeeRepository.findAll();
       List<EmployeeDto> employeesDto= employees.stream().map((employee)->EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
       return employeesDto;
    }

    public void deleteEmployee(Long id){
       Employee existingEmployee= employeeRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Employee","Id",id)
        );
        employeeRepository.deleteById(id);
    }

}
