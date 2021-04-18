package com.davidinnocent.employeemanager.controllers;

import com.davidinnocent.employeemanager.exceptions.UserNotFoundException;
import com.davidinnocent.employeemanager.model.Employee;
import com.davidinnocent.employeemanager.repo.EmployeeRepository;
import com.davidinnocent.employeemanager.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(service.getAllEmployees(),HttpStatus.OK);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.getSingleEmployee(id),HttpStatus.OK);
        }
        catch (UserNotFoundException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found in out DB");
        }
    }

    @PostMapping("/createNewEmployee")
    public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(service.saveEmployee(employee),HttpStatus.CREATED);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee) {

        try {
            Employee employeeInDatabase=service.getSingleEmployee(id);

            employee.setId(employeeInDatabase.getId());
            return new ResponseEntity<>(service.updateEmployee(employee),HttpStatus.OK);
        }
        catch (UserNotFoundException userNotFoundException)
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"The user was not found.");
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
        try{
            Employee employee=service.getSingleEmployee(id);
            service.deleteEmployeeById(id);
           return new ResponseEntity<>(employee,HttpStatus.OK);


        }
        catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found",exception);
        }

    }

}
