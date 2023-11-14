package com.ems.restapidto.mapper;

import com.ems.restapidto.dto.EmployeeDto;
import com.ems.restapidto.entity.Employee;

public class EmployeeMapper {

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        Employee employee=new Employee(
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getEmail()
        );
        return employee;
    }

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        EmployeeDto employeeDto=new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail()
        );
        return employeeDto;
    }


}
