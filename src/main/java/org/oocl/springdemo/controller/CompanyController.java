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
    private final List<Company> companies = new ArrayList<>();
    private int id = 0;

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> createCompany(Company company) {
        company.setId(++this.id);
        companies.add(company);
        return Map.of("id", company.getId());
    }

    @GetMapping("/companies")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        return companies;
    }

    @GetMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Void> updateCompanyById(@PathVariable("id") int id, @RequestBody Company newCompany) {
        Company company = companies.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (company != null) {
            company.setName(newCompany.getName());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") int id) {
        companies.removeIf(company -> company.getId() == id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/companies?page={page}&pageSize={pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompaniesByPage(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, companies.size());
        return companies.subList(start, end);
    }

    public void clearCompanies() {
        companies.clear();
        this.id = 0;
    }
}
