package com.mokal.mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
//    @NotNull(message = "Name is Required")
//    @NotEmpty(message = "name of The cannot be empty.")
    @NotBlank(message = "Name of the Employee canoot the blank")
    @Size(min = 3,max = 10, message = "Number Of charachter of name should range in 3 to 10")
    private String name;

    @NotBlank(message = "Email Of the Employee cannot Blank")
    @Email(message = "Email Should be a valid Format abc@xyz.com")
    private String email;

    @NotNull(message = "Age of emplaye cannot be blank")
    @Max(value = 80, message = "Age of Employee cannot be greater than 80")
    @Min(value = 18, message = "Age of The employee cannot be less than 18")
    private Integer age;

    @NotBlank(message = "Role Of the Employee cannot Blank")
    @Pattern(regexp = "^(ADMIN|USER)$" , message = "Role Of the Employee can User Or Admin")
    private String role;

    @PastOrPresent(message = "DateofJoining field in Employee cannot be in the future")
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private Boolean isActive;
}
