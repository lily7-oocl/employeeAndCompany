package org.oocl.springdemo.controller;

import org.oocl.springdemo.pojo.Company;
import org.oocl.springdemo.pojo.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {
    List<Company> companies = new ArrayList<>();
    @PostMapping("/companies")
    public Company createCompany(Company company) {
        company.setId(companies.size() + 1);
        companies.add(company);
        return company;
    }

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return companies;
    }

}
