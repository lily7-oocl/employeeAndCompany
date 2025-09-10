package org.oocl.springdemo.service;

import org.oocl.springdemo.common.EmployeeErrorStatus;
import org.oocl.springdemo.dao.EmployeeDao;
import org.oocl.springdemo.exception.EmployeeException;
import org.oocl.springdemo.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Map<String, Integer> createEmployee(Employee employee) {
        if (employee.getAge()<18 || employee.getAge()>65) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_NOT_IN_AMONG_AGE);
        }
        if (employee.getAge()>30 && employee.getSalary() < 20000){
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_AGE_OVER_30_AND_SALARY_BELOW_20000);
        }
        int id = employeeDao.create(employee);
        return Map.of("id", id);
    }

    public Employee getEmployeeById(int id) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_NOT_FOUND);
        }
        return employee;
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeDao.getByGender(gender);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getAll();
    }

    public void updateEmployee(int id, Employee newEmployee) {
        Employee employee = employeeDao.getById(id);
        employeeDao.update(employee,newEmployee);
    }

    public void deleteEmployee(int id) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_NOT_FOUND);
        }
        if (!employee.getStatus()){
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_ALREADY_DELETED);
        }
        employeeDao.removeById(id);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        return employeeDao.getByPage(page, pageSize);
    }
}
