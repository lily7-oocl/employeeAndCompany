package org.oocl.springdemo.controller;

import org.oocl.springdemo.pojo.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    @PostMapping("/employee")
    public ResponseEntity<Map<String, Integer>> createEmployee(@RequestBody Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", employee.getId()));
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployeesByGender(@RequestParam(required = false) String gender) {
        return gender!=null ? employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList())
                : employees;
    }

    @PutMapping("/employees/{id}")
    public boolean updateEmployee(@PathVariable("id") int id, @RequestBody Employee newEmployee) {
        Employee employee = employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (employee != null) {
            employee.setName(newEmployee.getName());
            employee.setAge(newEmployee.getAge());
            employee.setGender(newEmployee.getGender());
            employee.setSalary(newEmployee.getSalary());
            return true;
        }
        return false;
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity deleteEmployee(@PathVariable("id") int id) {
        employees.removeIf(employee -> employee.getId() == id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/employees?page={page}&pageSize={pageSize}")
    public List<Employee> getEmployeesByPage(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, employees.size());
        return employees.subList(start, end);
    }

}
