package com.kodilla.hibernate.manytomany.dao;

import com.kodilla.hibernate.manytomany.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeDaoTestSuite {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void testFindEmployeeByLastname() {
        //Given
        Employee johnSmith = new Employee("John", "Smith");
        Employee stephanieClarckson = new Employee("Stephanie", "Clarckson");
        Employee lindaKovalsky = new Employee("Linda", "Kovalsky");
        Employee robertSmith = new Employee("Robert", "Smith");

        employeeDao.save(johnSmith);
        int johnSmithId = johnSmith.getId();

        employeeDao.save(stephanieClarckson);
        int stephanieClarcskonId = stephanieClarckson.getId();

        employeeDao.save(lindaKovalsky);
        int lindaKovalskyId = lindaKovalsky.getId();

        employeeDao.save(robertSmith);
        int robertSmithId = robertSmith.getId();

        //When
        List<Employee> employees = employeeDao.findEmployeeByLastname("Smith");

        //Then
        assertEquals(2, employees.size());

        //Clean Up
        try {
            employeeDao.deleteById(johnSmithId);
            employeeDao.deleteById(stephanieClarcskonId);
            employeeDao.deleteById(lindaKovalskyId);
            employeeDao.deleteById(robertSmithId);
        } catch (Exception e) {
            //Do nothing
        }
    }
}
