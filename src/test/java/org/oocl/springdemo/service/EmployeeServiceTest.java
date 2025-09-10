package org.oocl.springdemo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.oocl.springdemo.dao.EmployeeDao;
import org.oocl.springdemo.exception.EmployeeException;
import org.oocl.springdemo.pojo.Employee;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.oocl.springdemo.common.EmployeeErrorStatus.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeDao employeeDao;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_not_create_employee_when_post_given_invalid_age_that_not_in_range_between_18_and_65() {
        EmployeeException employeeNotFoundException = assertThrows(EmployeeException.class, () -> employeeService.createEmployee(new Employee("Tom", 17, "Male", 5000.0)));
        assertEquals(EMPLOYEE_NOT_IN_AMONG_AGE.getMessage(), employeeNotFoundException.getMessage());
        verify(employeeDao, never()).create(any());
        EmployeeException employeeNotFoundException2 = assertThrows(EmployeeException.class, () -> employeeService.createEmployee(new Employee("Tom", 66, "Male", 5000.0)));
        assertEquals(EMPLOYEE_NOT_IN_AMONG_AGE.getMessage(), employeeNotFoundException2.getMessage());
        verify(employeeDao, never()).create(any());
    }

    @Test
    public void should_not_create_employee_when_post_given_over_30_age_and_salary_below_20000() {
        EmployeeException employeeUnvalidAgeAndSalaryException = assertThrows(EmployeeException.class, () -> employeeService.createEmployee(new Employee("Tom", 40, "Male", 5000.0)));
        assertEquals(EMPLOYEE_AGE_OVER_30_AND_SALARY_BELOW_20000.getMessage(),employeeUnvalidAgeAndSalaryException.getMessage());
        verify(employeeDao, never()).create(any());
    }

    @Test
    public void should_create_employee_when_post_given_valid_age_and_valid_salary() {
        Employee employee = new Employee("Tom", 18, "Male", 5000.0);
        Map<String, Integer> expectedId = new HashMap<>();
        expectedId.put("id", 0);
        when(employeeDao.create(employee)).thenReturn(employee.getId());
        Map<String, Integer> returnId = employeeService.createEmployee(employee);
        assertEquals(expectedId, returnId);
    }

    @Test
    public void should_not_return_employee_when_get_given_not_exist_id() {
        when(employeeDao.getById(1)).thenReturn(null);
        EmployeeException employeeNotFoundException = assertThrows(EmployeeException.class, () -> employeeService.getEmployeeById(1));
        assertEquals(EMPLOYEE_NOT_FOUND.getMessage(), employeeNotFoundException.getMessage());
        verify(employeeDao, times(1)).getById(1);
    }

    @Test
    public void should_return_employee_when_get_given_exist_id() {
        Employee employee = new Employee("Tom", 18, "Male", 5000.0);
        when(employeeDao.getById(1)).thenReturn(employee);
        assertEquals(employee, employeeDao.getById(1));
        verify(employeeDao, times(1)).getById(1);
    }

    @Test
    public void should_change_employee_status_when_delete_employ_given_exist_id() {
        Employee employee = new Employee("Tom", 18, "Male", 5000.0,true);
        when(employeeDao.getById(1)).thenReturn(employee);
        when(employeeDao.removeById(1)).thenReturn(true);
        assertDoesNotThrow(() -> employeeService.deleteEmployeeById(1));
        verify(employeeDao, times(1)).removeById(1);
    }

    @Test
    public void should_not_change_employee_status_when_delete_employee_given_not_exist_id() {
        when(employeeDao.getById(1)).thenReturn(null);
        EmployeeException employeeNotFoundException = assertThrows(EmployeeException.class, () -> employeeService.deleteEmployeeById(1));
        assertEquals(EMPLOYEE_NOT_FOUND.getMessage(), employeeNotFoundException.getMessage());
        verify(employeeDao, times(1)).getById(1);
    }

    @Test
    public void should_not_change_employee_status_when_delete_employee_given_already_deleted_employee_id() {
        Employee employee = new Employee("Tom", 18, "Male", 5000.0,false);
        when(employeeDao.getById(1)).thenReturn(employee);
        when(employeeDao.removeById(1)).thenReturn(true);
        EmployeeException employeeAlreadyDeletedException = assertThrows(EmployeeException.class, () -> employeeService.deleteEmployeeById(1));
        assertEquals(EMPLOYEE_ALREADY_DELETED.getMessage(), employeeAlreadyDeletedException.getMessage());
        verify(employeeDao, times(0)).removeById(1);
    }

    @Test
    public void should_update_employee_when_update_employee_given_exist_id_and_new_employee() {
        Employee newEmployee = new Employee("Jerry", 18, "Male", 5000);
        when(employeeDao.getById(1)).thenReturn(new Employee("Tom", 18, "Male", 5000.0,true));
        assertDoesNotThrow(() -> employeeService.updateEmployee(1, newEmployee));
    }

    @Test
    public void should_not_update_employee_when_update_employee_given_not_exist_id_and_new_employee() {
        Employee newEmployee = new Employee("Jerry", 18, "Male", 5000);
        when(employeeDao.getById(1)).thenReturn(null);
        EmployeeException employeeNotFoundException = assertThrows(EmployeeException.class, () -> employeeService.updateEmployee(1, newEmployee));
        assertEquals(EMPLOYEE_NOT_FOUND.getMessage(), employeeNotFoundException.getMessage());
        verify(employeeDao, times(0)).update(any(),any());
    }

    @Test
    public void should_not_update_employee_when_update_employee_given_exist_id_that_is_deleted_and_new_employee() {
        Employee newEmployee = new Employee("Jerry", 18, "Male", 5000);
        Employee oldEmployee = new Employee("Jerry", 18, "Male", 5000,false);
        when(employeeDao.getById(1)).thenReturn(oldEmployee);
        EmployeeException employeeAlreadyDeletedException = assertThrows(EmployeeException.class, () -> employeeService.updateEmployee(1, newEmployee));
        assertEquals(EMPLOYEE_ALREADY_DELETED.getMessage(), employeeAlreadyDeletedException.getMessage());
        verify(employeeDao, times(0)).update(any(),any());
    }
}
