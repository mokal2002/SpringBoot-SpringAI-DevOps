package com.mokal.mvc.services;


import com.mokal.mvc.config.MapperConfig;
import com.mokal.mvc.dto.EmployeeDTO;
import com.mokal.mvc.entities.EmployeeEntity;
import com.mokal.mvc.exceptions.ResourceNotFoundException;
import com.mokal.mvc.repository.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.expression.spel.support.ReflectiveIndexAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeByID(Long empID) {
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empID);
//        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class));
        return employeeRepository.findById(empID).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
    public boolean isExistsByEmployeeId(Long empId){
        boolean exists = employeeRepository.existsById(empId);
        if (!exists) throw new ResourceNotFoundException("Employee not found with this ID :"+empId);
        return true;
    }


    public EmployeeDTO createNewEmployee(EmployeeEntity inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(inputEmployee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);

    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Long empId) {
        isExistsByEmployeeId(empId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(empId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);

    }


    public void deleteEmployee(Long empId) {
        isExistsByEmployeeId(empId);
        employeeRepository.deleteById(empId);
    }

    public EmployeeDTO updatePartialEmployeeById(Long empId, Map<String, Object> updates) {
        isExistsByEmployeeId(empId);
        EmployeeEntity employeeEntity = employeeRepository.findById(empId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
