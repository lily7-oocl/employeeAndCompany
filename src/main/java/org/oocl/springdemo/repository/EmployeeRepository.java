package org.oocl.springdemo.repository;

import org.oocl.springdemo.entity.pojo.Employee;

import java.util.List;

public interface EmployeeRepository {
    public void deleteAll();
    public int create(Employee employee);
    public Employee getById(int id);
    public List<Employee> getByGender(String gender);
    public void update(Employee employee, Employee newEmployee);
    public void removeById(int id);
    public List<Employee> getByPage(int page, int pageSize);
    public List<Employee> getAll();
}
