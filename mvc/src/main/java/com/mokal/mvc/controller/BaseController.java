package com.mokal.mvc.controller;


import com.mokal.mvc.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class BaseController {

    @GetMapping(path = "/")
    public String Test(){
        return "Api Is working😍😍😍😍";
    }
    //Getting A Employee By Id
//    http://localhost:8080/emp/1
    @GetMapping("emp/{empID}")
    public EmployeeDTO getEmployeeById(@PathVariable Long empID){
        return new EmployeeDTO(empID,"Aniket","aniket@gmail.com",23, LocalDate.of(2026,03,02),true);
    }

// Getting Data With Params ?
//    http://localhost:8080/emp?age=23&sortBy=Aniket
    @GetMapping("/emp")
    public String getAllEmployess(@RequestParam Integer age,
                                  @RequestParam(required = false) String sortBy) {
        return "Hi Your Age Is "+age+" "+sortBy;
    }
}
