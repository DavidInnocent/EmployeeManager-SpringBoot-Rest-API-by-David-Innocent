package com.davidinnocent.employeemanager.repo;


import com.davidinnocent.employeemanager.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long>
{

    void deleteEmployeeById(Long id);
}
