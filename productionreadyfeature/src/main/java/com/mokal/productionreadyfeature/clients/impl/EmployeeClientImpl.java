package com.mokal.productionreadyfeature.clients.impl;

import com.mokal.productionreadyfeature.advice.ApiResponse;
import com.mokal.productionreadyfeature.clients.EmployeeClient;
import com.mokal.productionreadyfeature.dto.EmployeeDTO;
import com.mokal.productionreadyfeature.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;


    @Override
    public List<EmployeeDTO> getAllEmployees() {

        List<EmployeeDTO> employees = restClient.get()
                .uri("/emp")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EmployeeDTO>>() {});

        return employees;
    }

    @Override
    public EmployeeDTO getEmpById(Long empId) {
        try {
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.get()
                    .uri("emp/{empId}",empId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return employeeDTOApiResponse.getData();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmp(EmployeeDTO employeeDTO) {
        try {
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.post()
                    .uri("emp")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new ResourceNotFoundException("could not create the emp");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeDTOApiResponse.getData();

        }catch (Exception e){
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