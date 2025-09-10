package org.oocl.springdemo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.oocl.springdemo.dao.EmployeeDao;
import org.oocl.springdemo.exception.EmployeeNotFoundException;
import org.oocl.springdemo.exception.EmployeeNotInAmongAgeException;
import org.oocl.springdemo.pojo.Employee;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeDao employeeDao;
    @InjectMocks
    private EmployeeService employeeService;
    @Test
    public void should_not_create_employee_when_post_given_invalid_age(){
        assertThrows(EmployeeNotInAmongAgeException.class,()->employeeService.createEmployee(new Employee("Tom",17,"Male",5000.0)));
        verify(employeeDao, never()).create(any());
        assertThrows(EmployeeNotInAmongAgeException.class,()->employeeService.createEmployee(new Employee("Tom",66,"Male",5000.0)));
        verify(employeeDao, never()).create(any());
    }
    @Test
    public void should_create_employee_when_post_given_valid_age(){
        Employee employee = new Employee("Tom", 18, "Male", 5000.0);
        Map<String, Integer> expectedId = new HashMap<>();
        expectedId.put("id", 0);
        when(employeeDao.create(employee)).thenReturn(employee.getId());
        Map<String, Integer> returnId = employeeService.createEmployee(employee);
        assertEquals(expectedId, returnId);
    }
    @Test
    public void should_not_return_employee_when_get_given_not_exist_id(){
        when(employeeDao.getById(1)).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class,()->employeeService.getEmployeeById(1));
        verify(employeeDao,times(1)).getById(1);
    }
    @Test
    public void should_return_employee_when_get_given_exist_id(){
        Employee employee = new Employee("Tom", 18, "Male", 5000.0);
        when(employeeDao.getById(1)).thenReturn(employee);
        assertEquals(employee,employeeDao.getById(1));
        verify(employeeDao,times(1)).getById(1);
    }
}
