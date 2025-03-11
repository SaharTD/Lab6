package com.example.employeemanagementsystem.Controller;


import com.example.employeemanagementsystem.Api.ApiResponse;
import com.example.employeemanagementsystem.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {


    ArrayList<Employee> employees=new ArrayList<>();



    @GetMapping("get")
    public ResponseEntity getEmployee(){
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }



    @PostMapping("add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors error){
     if(error.hasErrors()){
         String message = error.getFieldError().getDefaultMessage();
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message));
     }
employees.add(employee);
return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee added successfully "));
    }



    @PutMapping("update/{id}")
    public ResponseEntity updateEmployee(@RequestBody @Valid Employee employee,Errors error , @PathVariable String id){
        if(error.hasErrors()){
            String massage=error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(massage));

        }
        for (Employee e : employees) {
            if(e.getId().equalsIgnoreCase(id)) {
               employees.set(employees.indexOf(e), employee);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee updated successfully"));
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found"));



    }



    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id){
        for (Employee e :employees) {
            if(e.getId().equalsIgnoreCase(id)){
                employees.remove(e);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee deleted successfully"));
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found"));

    }


// Search Employees  by Position
    @GetMapping("search/{position}")
    public ResponseEntity  searchEmployee(@PathVariable String position){
        ArrayList<Employee>supervisor=new ArrayList<>();
        ArrayList<Employee>coordinator=new ArrayList<>();

        if (position.equalsIgnoreCase("supervisor") ){
            for(Employee e :employees){
                if (e.getPosition().equalsIgnoreCase("supervisor")){
                    supervisor.add(e);
                }
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The employees with position -- "+position+"are :"+supervisor));
            }
        }else if(position.equalsIgnoreCase("coordinator")){
            for(Employee e :employees){
                if (e.getPosition().equalsIgnoreCase("coordinator")){
                    coordinator.add(e);
                }
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The employees with position -- "+position+"are :"+coordinator));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("the position you entered is not found"));
    }




//Get Employees by Age Range
    @GetMapping("ageRange/{min}/{max}")
    public ResponseEntity ageRange (@PathVariable int min , @PathVariable int max){
        ArrayList<Employee>within_ageRange=new ArrayList<>();
        if(min>=25 && max <=70){
            for (Employee e :employees){
                if(e.getAge()>=min && e.getAge()<=max){
                    within_ageRange.add(e);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The employees within the age range :"+within_ageRange));

        }else  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("the minimum and maximum values you entered does not match with the company age range from 25 to 70"));

    }




    @GetMapping("applyForAnnualLeave/{id}")
    public ResponseEntity applyForAnnualLeave( Employee employee,@PathVariable String id){

        for(Employee e  :employees) {
            if (e.getId().equalsIgnoreCase(id)){
                if(!e.isOnLeave()){
                    if(e.getAnnualLeave()>0){
                        e.setOnLeave(true);
                        e.setAnnualLeave(e.getAnnualLeave()-1);
                        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The Employee annual leave was approved "));
                    }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee annual leave days =0 "));

                }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee is on Vacation  "));
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found  "));
    }




    @GetMapping("hasNoAnnualLeave")
    public ResponseEntity hasNoAnnualLeave(){
        ArrayList<Employee>hasNoAnnualLeave =new ArrayList<>();
        for (Employee e: employees){
            if(e.getAnnualLeave()==0){
                hasNoAnnualLeave.add(e);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employees with 0 annual leave :"+hasNoAnnualLeave));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employees with no annual leaves are not found"));


    }


@PutMapping("promote/{supervisor_id}/{coordinator_id}")
public ResponseEntity promoteEmployee(@PathVariable String supervisor_id,@PathVariable String coordinator_id){
for(Employee e :employees) {
    if (e.getId().equalsIgnoreCase(supervisor_id)&&e.getPosition().equalsIgnoreCase("supervisor")){
        if (e.getId().equalsIgnoreCase(coordinator_id)&& e.getAge()<30 && e.isOnLeave()){
            e.setPosition("Supervisor");
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employees "+e.getName()+" was promoted successfully"));
        }
      else  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The employee did not meet the promotion criteria "));
        }

}
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Permission to promote was not granted "));

}















}















