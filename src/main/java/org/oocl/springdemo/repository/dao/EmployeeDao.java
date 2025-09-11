package org.oocl.springdemo.repository.dao;

import org.oocl.springdemo.entity.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
    List<Employee> getByGender(String gender);
}
