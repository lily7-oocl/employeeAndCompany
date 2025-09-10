package org.oocl.springdemo.service;

import org.oocl.springdemo.pojo.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    List<Employee> employees = new ArrayList<>();
    private int id = 0;

    public Map<String, Integer> createEmployee(Employee employee) {
        employee.setId(++this.id);
        employees.add(employee);
        return Map.of("id", employee.getId());
    }

    public Employee getEmployee(int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void updateEmployee(int id, Employee newEmployee) {
        Employee employee = employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (employee != null) {
            employee.setName(newEmployee.getName());
            employee.setAge(newEmployee.getAge());
            employee.setGender(newEmployee.getGender());
            employee.setSalary(newEmployee.getSalary());
        }
    }

    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        if (page < 0) {
            return null;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, employees.size());
        return employees.subList(start, end);
    }

    public void clearEmployees() {
        employees.clear();
        this.id = 0;
    }
}
