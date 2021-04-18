package com.davidinnocent.employeemanager.controllers;

import com.davidinnocent.employeemanager.exceptions.UserNotFoundException;
import com.davidinnocent.employeemanager.model.Employee;
import com.davidinnocent.employeemanager.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(),HttpStatus.OK);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(employeeService.getSingleEmployee(id),HttpStatus.OK);
        }
        catch (UserNotFoundException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found in out DB");
        }
    }

    @PostMapping("/createNewEmployee")
    public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee),HttpStatus.CREATED);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee) {

        try {
            Employee employeeInDatabase=employeeService.getSingleEmployee(id);

            employee.setId(employeeInDatabase.getId());
            return new ResponseEntity<>(employeeService.updateEmployee(employee),HttpStatus.OK);
        }
        catch (UserNotFoundException userNotFoundException)
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"The user was not found.");
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
        try{
            Employee employee=employeeService.getSingleEmployee(id);
            employeeService.deleteEmployeeById(id);
           return new ResponseEntity<>(employee,HttpStatus.OK);


        }
        catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found",exception);
        }

    }

}
