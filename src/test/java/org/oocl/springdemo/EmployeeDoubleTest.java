package org.oocl.springdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.oocl.springdemo.dao.EmployeeDao;
import org.oocl.springdemo.exception.EmployeeNotInAmongAgeException;
import org.oocl.springdemo.pojo.Employee;
import org.oocl.springdemo.service.EmployeeService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class EmployeeDoubleTest {
    @Mock
    private EmployeeDao employeeDao;
    @InjectMocks
    private EmployeeService employeeService;
    @Test
    public void should_not_create_employee_when_post_given_invalid_age(){
        assertThrows(EmployeeNotInAmongAgeException.class,()->employeeService.createEmployee(new Employee("Tom",17,"Male",5000.0)));
    }
}
