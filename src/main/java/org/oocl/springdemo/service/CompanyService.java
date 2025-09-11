package org.oocl.springdemo.service;

import jakarta.annotation.Resource;
import org.oocl.springdemo.exception.CompanyException;
import org.oocl.springdemo.entity.pojo.Company;
import org.oocl.springdemo.repository.CompanyRepository;
import org.oocl.springdemo.repository.impl.CompanyRepositoryDaoImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.oocl.springdemo.common.CompanyErrorStatus.COMPANY_NOT_FOUND;

@Service
public class CompanyService {
    @Resource(type = CompanyRepositoryDaoImpl.class)
    private CompanyRepository companyRepository;

    public int addCompany(Company company) {
        return companyRepository.add(company);
    }

    public Company getCompanyById(int id) {
        Company company = companyRepository.getById(id);
        if (company == null) {
            throw new CompanyException(COMPANY_NOT_FOUND);
        }
        return company;
    }

    public void updateCompany(int id, Company newCompany) {
        newCompany.setId(id);
        Company company = getCompanyById(id);
        companyRepository.update(company, newCompany);
    }

    public void deleteCompanyById(int id) {
        companyRepository.deleteById(id);
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        return companyRepository.getByPage(page, pageSize);
    }

    public List<Company> getCompanies() {
        return companyRepository.getAll();
    }
}
