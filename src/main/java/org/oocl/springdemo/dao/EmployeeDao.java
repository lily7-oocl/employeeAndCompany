package org.oocl.springdemo.dao;

import org.oocl.springdemo.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeDao {
    List<Employee> employees = new ArrayList<>();
    private int id = 0;

    public void deleteAll() {
        employees.clear();
        this.id = 0;
    }

    public int create(Employee employee) {
        employee.setId(++this.id);
        employees.add(employee);
        return this.id;
    }

    public Employee getById(int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }

    public List<Employee> getByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public void update(Employee employee, Employee newEmployee) {
            employee.setName(newEmployee.getName());
            employee.setAge(newEmployee.getAge());
            employee.setGender(newEmployee.getGender());
            employee.setSalary(newEmployee.getSalary());
    }

    public void removeById(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    public List<Employee> getByPage(int page, int pageSize) {
        if (page < 0) {
            return null;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, employees.size());
        return employees.subList(start, end);
    }

    public List<Employee> getAll() {
        return employees;
    }
}
