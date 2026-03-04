package com.mokal.mvc.services;


import com.mokal.mvc.config.MapperConfig;
import com.mokal.mvc.dto.EmployeeDTO;
import com.mokal.mvc.entities.EmployeeEntity;
import com.mokal.mvc.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeByID(Long empID) {
        EmployeeEntity employeeEntity = employeeRepository.findById(empID).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }


    public EmployeeDTO createNewEmployee(EmployeeEntity inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(inputEmployee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);

    }
}
