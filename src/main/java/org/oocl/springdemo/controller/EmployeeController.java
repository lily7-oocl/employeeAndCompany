package org.oocl.springdemo.controller;

import org.oocl.springdemo.entity.dto.UpdateEmployeeDto;
import org.oocl.springdemo.entity.pojo.Employee;
import org.oocl.springdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable("id") int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping(value = "", params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") int id, @RequestBody UpdateEmployeeDto updateEmployeeDto) {
        employeeService.updateEmployee(id, updateEmployeeDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable("id") int id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "", params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) {
        return employeeService.getEmployeesByPage(page, pageSize);
    }
}
