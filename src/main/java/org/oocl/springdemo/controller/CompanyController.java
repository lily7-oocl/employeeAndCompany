package org.oocl.springdemo.controller;

import org.oocl.springdemo.pojo.Company;
import org.oocl.springdemo.pojo.Employee;
import org.oocl.springdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> createCompany(Company company) {
        int id = companyService.addCompany(company);
        return Map.of("id", id);
    }

    @GetMapping("/companies")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable("id") int id, @RequestBody Company newCompany) {
        companyService.updateCompany(newCompany);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") int id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/companies",params = {"page","pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize) {
        return companyService.getCompanyByPage(page,pageSize);
    }
}
