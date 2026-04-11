package com.mokal.productionreadyfeature.clients;

import com.mokal.productionreadyfeature.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmpById(Long empId);

    EmployeeDTO createNewEmp(EmployeeDTO employeeDTO);
}
