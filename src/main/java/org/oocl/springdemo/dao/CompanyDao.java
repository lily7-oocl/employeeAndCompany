package org.oocl.springdemo.dao;

import org.oocl.springdemo.pojo.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDao {
    private final List<Company> companies = new ArrayList<>();
    private int id = 0;

    public void deleteAll() {
        companies.clear();
        this.id = 0;
    }

    public int add(Company company) {
        company.setId(++this.id);
        companies.add(company);
        return company.getId();
    }

    public Company getById(int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

    public void update(Company company, Company newCompany) {
            company.setName(newCompany.getName());
    }

    public void deleteById(int id) {
        companies.removeIf(company -> company.getId() == id);
    }

    public List<Company> getByPage(int page, int pageSize) {
        if (page < 0) {
            return null;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, companies.size());
        return companies.subList(start, end);
    }

    public List<Company> getAll() {
        return companies;
    }
}
