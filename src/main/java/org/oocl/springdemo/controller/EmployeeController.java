package org.oocl.springdemo.controller;

import org.oocl.springdemo.pojo.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();
    private int id = 0;

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> createEmployee(@RequestBody Employee employee) {
        employee.setId(++this.id);
        employees.add(employee);
        return Map.of("id", employee.getId());
    }

    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable("id") int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }

    @GetMapping(value = "/employees",params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    @GetMapping(value = "/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesByGender() {
        return employees;
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") int id, @RequestBody Employee newEmployee) {
        Employee employee = employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (employee != null) {
            employee.setName(newEmployee.getName());
            employee.setAge(newEmployee.getAge());
            employee.setGender(newEmployee.getGender());
            employee.setSalary(newEmployee.getSalary());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        employees.removeIf(employee -> employee.getId() == id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path="/employees",params = {"page","pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, employees.size());
        return employees.subList(start, end);
    }
    public void clearEmployees() {
        employees.clear();
        this.id = 0;
    }

}
