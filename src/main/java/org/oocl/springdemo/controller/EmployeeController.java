package org.oocl.springdemo.controller;

import org.oocl.springdemo.pojo.Employee;
import org.oocl.springdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Integer>> createEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok().body(employeeService.createEmployee(employee));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/employees", params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping(value = "/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesByGender() {
        return employeeService.getEmployees();
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") int id, @RequestBody Employee newEmployee) {
        employeeService.updateEmployee(id, newEmployee);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/employees", params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) {
        return employeeService.getEmployeesByPage(page, pageSize);
    }
}
