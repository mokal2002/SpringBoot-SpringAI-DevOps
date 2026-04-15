package com.mokal.productionreadyfeature.clients.impl;

import com.mokal.productionreadyfeature.advice.ApiResponse;
import com.mokal.productionreadyfeature.clients.EmployeeClient;
import com.mokal.productionreadyfeature.dto.EmployeeDTO;
import com.mokal.productionreadyfeature.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;


    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("Trying to retrieve all employees in getAllEmployees");
        try{
            ApiResponse<List<EmployeeDTO>> employees = restClient.get()
                    .uri("/emp")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.debug("Successfully retrieve data from getAllEmployess()");
            assert employees != null;
            log.trace("Retrieve  employee List from getAllEmployees {}", employees.getData());

            return employees.getData();
        }catch (Exception e){
            log.error("Exception Occurred in getAllEmployees() {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO getEmpById(Long empId) {
        try {
            log.info("Connecting to Data");
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.get()
                    .uri("emp/{empId}",empId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the emp");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            assert employeeDTOApiResponse != null;
            return employeeDTOApiResponse.getData();

        }catch (Exception e){
            log.error("Exception Occurred in getEmpById() {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmp(EmployeeDTO employeeDTO) {
        log.trace("trying to create Employee with Given Information {}",employeeDTO);
        try {
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.post()
                    .uri("emp")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.debug("4xxClient error occured during createNewEmployee()");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the emp");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.trace("Succsesfully created a new employeee: {}",employeeDTOApiResponse.getData());
            assert employeeDTOApiResponse != null;
            return employeeDTOApiResponse.getData();

        }catch (Exception e){
            log.error("Exception Occurred in createNewEmp() {}", String.valueOf(e));

            throw new RuntimeException(e);
        }
    }


//    @Override
//    public List<EmployeeDTO> getAllEmployees() {
//
//        ApiResponse<List<EmployeeDTO>> response = restClient.get()
//                .uri("/emp") // better to use leading slash
//                .retrieve()
//                .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {});
//
//        return response.getData();
//    }
}