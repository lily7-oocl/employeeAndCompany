package org.oocl.springdemo.repository;

import org.oocl.springdemo.entity.pojo.Company;

import java.util.List;

public interface CompanyRepository {
    public void deleteAll();

    public int add(Company company);

    public Company getById(int id);

    public void update(Company company, Company newCompany);

    public void deleteById(int id);

    public List<Company> getByPage(int page, int pageSize);

    public List<Company> getAll();
}
