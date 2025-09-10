package org.oocl.springdemo.service;

import org.oocl.springdemo.dao.EmployeeDao;
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
        int id = employeeDao.create(employee);
        return Map.of("id", employee.getId());
    }

    public Employee getEmployeeById(int id) {
        return employeeDao.getById(id);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeDao.getByGender(gender);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getAll();
    }

    public void updateEmployee(int id, Employee newEmployee) {
        employeeDao.update(id, newEmployee);
    }

    public void deleteEmployee(int id) {
        employeeDao.removeById(id);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        return employeeDao.getByPage(page, pageSize);
    }
}
