package org.oocl.springdemo.repository.impl;

import org.oocl.springdemo.entity.pojo.Employee;
import org.oocl.springdemo.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    List<Employee> employees = new ArrayList<>();
    private int id = 0;

    @Override
    public void deleteAll() {
        employees.clear();
        this.id = 0;
    }

    @Override
    public int create(Employee employee) {
        employee.setId(++this.id);
        employee.setStatus(true);
        employees.add(employee);
        return this.id;
    }

    @Override
    public Employee getById(int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Employee> getByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    @Override
    public void update(Employee employee, Employee newEmployee) {
            employee.setName(newEmployee.getName());
            employee.setAge(newEmployee.getAge());
            employee.setGender(newEmployee.getGender());
            employee.setSalary(newEmployee.getSalary());
    }

    @Override
    public void removeById(int id) {
        employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .map(employee -> {
                    employee.setStatus(false);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<Employee> getByPage(int page, int pageSize) {
        if (page < 0) {
            return null;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, employees.size());
        return employees.subList(start, end);
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }
}
