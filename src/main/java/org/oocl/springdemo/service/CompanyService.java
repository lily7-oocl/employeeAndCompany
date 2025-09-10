package org.oocl.springdemo.service;

import org.oocl.springdemo.pojo.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final List<Company> companies = new ArrayList<>();
    private int id = 0;

    public void clearCompanies() {
        companies.clear();
        this.id = 0;
    }

    public int addCompany(Company company) {
        company.setId(++this.id);
        companies.add(company);
        return company.getId();
    }

    public Company getCompanyById(int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

    public void updateCompany(Company newCompany) {
        Company company = companies.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (company != null) {
            company.setName(newCompany.getName());
        }
    }

    public void deleteCompanyById(int id) {
        companies.removeIf(company -> company.getId() == id);
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        if (page < 0) {
            return null;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, companies.size());
        return companies.subList(start, end);
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
