package org.oocl.springdemo.service;

import jakarta.annotation.Resource;
import org.oocl.springdemo.common.EmployeeErrorStatus;
import org.oocl.springdemo.entity.dto.UpdateEmployeeDto;
import org.oocl.springdemo.exception.EmployeeException;
import org.oocl.springdemo.entity.pojo.Employee;
import org.oocl.springdemo.repository.EmployeeRepository;
import org.oocl.springdemo.repository.impl.EmployeeRepositoryDaoImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class EmployeeService {
    @Resource(type = EmployeeRepositoryDaoImpl.class)
//    @Resource(type = EmployeeRepositoryImpl.class)
    private EmployeeRepository EmployeeRepository;

    //TODO same gender and name can not create
    public Map<String, Integer> createEmployee(Employee employee) {
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_NOT_IN_AMONG_AGE);
        }
        if (employee.getAge() >= 30 && employee.getSalary() < 20000) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_AGE_OVER_AND_INCLUSIVE_30_AND_SALARY_BELOW_20000);
        }
        int id = EmployeeRepository.create(employee);
        return Map.of("id", id);
    }

    public Employee getEmployeeById(int id) {
        Employee employee = EmployeeRepository.getById(id);
        if (employee == null) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_NOT_FOUND);
        }
        return employee;
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return EmployeeRepository.getByGender(gender);
    }

    public List<Employee> getEmployees() {
        return EmployeeRepository.getAll();
    }

    public void updateEmployee(int id, UpdateEmployeeDto updateEmployeeDto) {
        Employee employee = this.getEmployeeById(id);
        if (!employee.getStatus()) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_ALREADY_DELETED);
        }
        employee.setAge(updateEmployeeDto.getAge());
        employee.setName(updateEmployeeDto.getName());
        employee.setSalary(updateEmployeeDto.getSalary());
        EmployeeRepository.update(employee, employee);
    }

    public void deleteEmployeeById(int id) {
        Employee employee = this.getEmployeeById(id);
        if (!employee.getStatus()) {
            throw new EmployeeException(EmployeeErrorStatus.EMPLOYEE_ALREADY_DELETED);
        }
        EmployeeRepository.removeById(id);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        return EmployeeRepository.getByPage(page, pageSize);
    }
}
