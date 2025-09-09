package org.oocl.springdemo.controller;

import org.oocl.springdemo.pojo.Company;
import org.oocl.springdemo.pojo.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    @PostMapping("/companies")
    public ResponseEntity<Map<String, Integer>> createCompany(Company company) {
        company.setId(companies.size() + 1);
        companies.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", company.getId()));
    }

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return companies;
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

    @PutMapping("/companies/{id}")
    public boolean updateCompanyById(@PathVariable("id") int id, @RequestBody Company newCompany) {
        Company company = companies.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (company != null) {
            company.setName(newCompany.getName());
            return true;
        }
        return false;
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity deleteCompany(@PathVariable("id") int id) {
        companies.removeIf(company -> company.getId() == id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
