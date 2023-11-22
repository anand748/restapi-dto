package com.ems.restapidto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.links.Link;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Rest API documentation",
				description = "Spring Boot Rest API documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Sourabh Anand",
						email = "sourabhanand123@gmail.com",
						url = "https://wwww.anand123.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.javaguides.net/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				 description = "Spring boot rest api Employee Management external documentation",
				url="https://wwww.anand123.com/employee_management"
		)
)
@SpringBootApplication
public class RestapiDtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiDtoApplication.class, args);
	}

}
