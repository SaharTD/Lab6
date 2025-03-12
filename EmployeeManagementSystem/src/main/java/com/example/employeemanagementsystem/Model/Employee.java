package com.example.employeemanagementsystem.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.message.Message;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
//ID , name, email , phoneNumber ,age, position, onLeave, hireDate and
//annualLeave.




    @NotEmpty(message = "The id can not be empty")
    @Size(max = 2,message = "Length must be more than 2 characters")
    private String id;




    @NotEmpty(message = "The name can not be empty")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "name contains only characters not numbers")
    private String name;


    @Email(message = "Please enter the email in the correct format")
    private String email;


    @Pattern(regexp = "^05-[0-9]{8}$", message = "The phone number must start with 05 and contains 10 number ex: 0554603625")
    private int phoneNumber;


    @NotNull(message = "the age can not be null")
    @Pattern(regexp = "^[0-9]+$",message = "Age must contains only numbers")
    @Min(value = 25,message = "age must be more than 25" )
    @Max(value = 70,message = "age must be less than 70")
    private int age;

@NotEmpty (message ="The position can not be empty")
@Pattern(regexp = "^(supervisor|coordinator)$",message ="position must be 'supervisor' or 'coordinator'" )
    private String position ;


@AssertFalse
private boolean onLeave ;


@JsonFormat(pattern ="yyyy-MM-dd")
@PastOrPresent(message = "should be a date in the present or the past.")
private LocalDate hireDate;


@NotNull (message = " Annual Leave cant be null")
@Positive(message="Annual leave must be a positive number ")
private int  AnnualLeave;



}
