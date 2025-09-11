package org.oocl.springdemo.repository.impl;

import org.oocl.springdemo.entity.pojo.Company;
import org.oocl.springdemo.repository.CompanyRepository;
import org.oocl.springdemo.repository.dao.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepositoryDaoImpl implements CompanyRepository {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public void deleteAll() {
        companyDao.deleteAll();
    }

    @Override
    public int add(Company company) {
        return companyDao.save(company).getId();
    }

    @Override
    public Company getById(int id) {
        return companyDao.findById(id).isPresent() ? companyDao.findById(id).get() : null;
    }

    @Override
    public void update(Company company, Company newCompany) {
        companyDao.save(newCompany);
    }

    @Override
    public void deleteById(int id) {
        companyDao.deleteById(id);
    }

    @Override
    public List<Company> getByPage(int page, int pageSize) {
        Pageable Pageable = PageRequest.of(page - 1, pageSize);
        return companyDao.findAll(Pageable).getContent();
    }

    @Override
    public List<Company> getAll() {
        return companyDao.findAll();
    }
}
