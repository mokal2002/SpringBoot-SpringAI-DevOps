package com.mokal.mvc.controller;


import com.mokal.mvc.dto.EmployeeDTO;
import com.mokal.mvc.entities.EmployeeEntity;
import com.mokal.mvc.repository.EmployeeRepository;
import com.mokal.mvc.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/emp")
public class BaseController {
//    Controller Layer
//    private final EmployeeRepository employeeRepository;
//
//    public BaseController(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    //Service Layer
    private final EmployeeService employeeService;

    public BaseController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/test")
    public String Test(){
        return "Api Is working😍😍😍😍";
    }


    //Getting A Employee By Id
    //http://localhost:8080/emp/1
//    @GetMapping("emp/{empID}")
//    public EmployeeDTO getEmployeeById(@PathVariable Long empID){
//        return new EmployeeDTO(empID,"Aniket","aniket@gmail.com",23, LocalDate.of(2026,03,02),true);
//    }

    @GetMapping("{empID}")
    public EmployeeDTO getEmployeeById(@PathVariable Long empID){
        return employeeService.getEmployeeByID(empID);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);

    }

    @PutMapping("/{empId}")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long empId){
        return employeeService.updateEmployee(employeeDTO, empId);
    }

    @DeleteMapping("/{empId}")
    public void deleteEmployee(@PathVariable Long empId){
        employeeService.deleteEmployee(empId);
    }

    @PatchMapping("/{empId}")
    public EmployeeDTO updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long empId){
        return employeeService.updatePartialEmployeeById(empId, updates);
    }

    // Getting Data With Params ?
    //http://localhost:8080/emp?age=23&sortBy=Aniket
//    @GetMapping("/emp")
//    public String getAllEmployess(@RequestParam Integer age,
//                                  @RequestParam(required = false) String sortBy) {
//        return "Hi Your Age Is "+age+" "+sortBy;
//    }

//    @GetMapping("/all")
//    public List<EmployeeEntity> getAllEmployess(@RequestParam Integer age,
//                                                @RequestParam(required = false) String sortBy) {
//        return employeeRepository.findAll();
//    }

//    @GetMapping
//    public List<EmployeeEntity> getAllEmployess(){
//        return employeeRepository.findAll();
//    }
//
//    @PostMapping
//    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
//        return employeeRepository.save(inputEmployee);
//
//    }
}
