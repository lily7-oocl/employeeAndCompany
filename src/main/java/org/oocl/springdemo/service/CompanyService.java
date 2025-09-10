package org.oocl.springdemo.service;

import org.oocl.springdemo.dao.CompanyDao;
import org.oocl.springdemo.pojo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public int addCompany(Company company) {
        return companyDao.add(company);
    }

    public Company getCompanyById(int id) {
        return companyDao.getCompanyById(id);
    }

    public void updateCompany(int id, Company newCompany) {
        companyDao.update(id, newCompany);
    }

    public void deleteCompanyById(int id) {
        companyDao.deleteById(id);
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        return companyDao.getByPage(page, pageSize);
    }

    public List<Company> getCompanies() {
        return companyDao.getAll();
    }
}
