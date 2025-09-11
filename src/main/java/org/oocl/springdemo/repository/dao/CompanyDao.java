package org.oocl.springdemo.repository.dao;

import org.oocl.springdemo.entity.pojo.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDao extends JpaRepository<Company, Integer> {
}
