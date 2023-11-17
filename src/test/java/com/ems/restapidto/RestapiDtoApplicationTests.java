package com.ems.restapidto;

import com.ems.restapidto.dto.EmployeeDto;
import com.ems.restapidto.entity.Employee;
import com.ems.restapidto.exception.EmailAlreadyExistException;
import com.ems.restapidto.mapper.EmployeeMapper;
import com.ems.restapidto.repository.EmployeeRepository;
import com.ems.restapidto.service.EmployeeService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestapiDtoApplicationTests {

	@Mock
	EmployeeRepository employeeRepository;

	@InjectMocks
	EmployeeService employeeService;

	@InjectMocks
	EmployeeMapper employeeMapper;


	@Test
	public void test_getEmployees(){


		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee(1L,"sourabh","sourabh123@gmail.com"));
		employeeList.add(new Employee(2L,"anand","anand123@gmail.com"));

		when(employeeRepository.findAll()).thenReturn(employeeList);
		assertEquals(employeeService.getEmployees().size(),2);
	}
   @Test
    public void test_getEmployee(){
		Employee employee=new Employee(1L,"sourabh","sourabh123@gmail.com");
	 when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
	 assertEquals(employee.getId(),employeeService.getEmployee(1L).getId());
	}

	@Test
	public void test_deleteEmployee(){

		Employee employee=new Employee(1L,"sourabh","sourabh123@gmail.com");


		when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
		employeeService.deleteEmployee(employee.getId());
		verify(employeeRepository,times(1)).deleteById(employee.getId());
	}

//	@Test
//	public void test_updateEmployee(){
//
//
//		Employee employee =new Employee(1L,"sourabh","sourabh123@gmail.com");
//		EmployeeDto employeeDto= employeeMapper.mapToEmployeeDto(employee);
//
////		when(employeeRepository.save(employee)).thenReturn(employee);
//
//
//		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
//
//		assertEquals(1,employeeService.createEmployee(employeeDto).getId());
//
//
//	}

	@Test
	public void test_updateEmployee(){
		Employee employee =new Employee(1L,"sourabh","sourabh123@gmail.com");


		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		employee.setName("anand");
		employee.setEmail("anand123@gmail.com");

		EmployeeDto employeeDto= employeeMapper.mapToEmployeeDto(employee);

		when(employeeRepository.save(employee)).thenReturn(employee);
		assertEquals("anand",employeeService.updateEmployee(employeeDto).getName());
	}

	@Test
	public void test_createEmployeeSucess(){

//		test is passing because, during the execution of createEmployee, the findByEmail
//		method is returning an empty Optional<Employee>. Therefore, the createEmployee method
//		successfully proceeds to save the new employee without throwing the
//		EmailAlreadyExistException.

		Employee employee =new Employee();
		employee.setName("sourabh");
		employee.setEmail("sourabh123@gmail.com");

		when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
			Employee savedEmployee = invocation.getArgument(0);
			savedEmployee.setId(1L);
			return savedEmployee;
		});


		EmployeeDto employeeDto=employeeMapper.mapToEmployeeDto(employee);
		assertEquals("sourabh",employeeService.createEmployee(employeeDto).getName());

	}


	@Test
	public void test_createEmployeeEmailAlreadyExists() {
		// Mocking an existing employee with the same email
		Employee existingEmployee = new Employee();
		existingEmployee.setName("John");
		existingEmployee.setEmail("sourabh123@gmail.com");

		when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(existingEmployee));

		// Creating a new employee with the same email
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("Sourabh");
		employeeDto.setEmail("sourabh123@gmail.com");

		// Verifying that EmailAlreadyExistException is thrown

//		The assertThrows method in Java is used to verify that a specific exception is
//		thrown when executing a piece of code

		assertThrows(EmailAlreadyExistException.class, () -> {
			employeeService.createEmployee(employeeDto);
		});
	}



}
