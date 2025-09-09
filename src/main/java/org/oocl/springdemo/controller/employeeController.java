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
public class employeeController {
    List<Employee> employees = new ArrayList<>();
    @PostMapping("/employee")
    public ResponseEntity<Map<String,Integer>> createEmployee(@RequestBody Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",employee.getId()));
    }
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }
    @GetMapping("/employees")
    public List<Employee> getEmployees(@RequestParam String gender) {
        return employees.stream().filter(employee -> employee.getGender() == gender).collect(Collectors.toList());
    }

}
