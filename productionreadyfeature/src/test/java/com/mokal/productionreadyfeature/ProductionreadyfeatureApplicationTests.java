package com.mokal.productionreadyfeature;

import com.mokal.productionreadyfeature.clients.EmployeeClient;
import com.mokal.productionreadyfeature.dto.EmployeeDTO;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ProductionreadyfeatureApplicationTests {
	@Autowired
	private EmployeeClient employeeClient;

	@Test
	void getAllEmployess(){
		List<EmployeeDTO> employeeDTOS = employeeClient.getAllEmployees();
		System.out.println(employeeDTOS);
	}

	@Test
	void getEmpById(){
		EmployeeDTO employeeDTO = employeeClient.getEmpById(1L);
		System.out.println(employeeDTO);
	}

	@Test
	void createEmp(){
		EmployeeDTO employeeDTO= new EmployeeDTO(null,"Aniket","aniket@gmail.com",2, "USER", LocalDate.of(2020,12,02), true);
		EmployeeDTO savedEmp= employeeClient.createNewEmp(employeeDTO);
		System.out.println(savedEmp);
	}
}
