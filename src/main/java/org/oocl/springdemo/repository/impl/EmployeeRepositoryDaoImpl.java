package org.oocl.springdemo.repository.impl;

import org.oocl.springdemo.entity.pojo.Employee;
import org.oocl.springdemo.repository.EmployeeRepository;
import org.oocl.springdemo.repository.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryDaoImpl implements EmployeeRepository {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void deleteAll() {
        employeeDao.deleteAll();
    }

    @Override
    public int create(Employee employee) {
        employee.setStatus(true);
        return employeeDao.save(employee).getId();
    }

    @Override
    public Employee getById(int id) {
        return employeeDao.findById((long) id).isPresent() ? employeeDao.findById((long) id).get() : null;
    }

    @Override
    public List<Employee> getByGender(String gender) {
        return employeeDao.getByGender(gender);
    }

    @Override
    public void update(Employee employee, Employee newEmployee) {
        employeeDao.save(newEmployee);
    }

    @Override
    public void removeById(int id) {
        employeeDao.deleteById((long) id);
    }

    @Override
    public List<Employee> getByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return employeeDao.findAll(pageable).getContent();
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.findAll();
    }
}
