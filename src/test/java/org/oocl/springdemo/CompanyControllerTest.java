package org.oocl.springdemo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oocl.springdemo.controller.CompanyController;
import org.oocl.springdemo.entity.pojo.Company;
import org.oocl.springdemo.entity.pojo.Employee;
import org.oocl.springdemo.repository.CompanyRepository;
import org.oocl.springdemo.repository.EmployeeRepository;
import org.oocl.springdemo.repository.impl.CompanyRepositoryDaoImpl;
import org.oocl.springdemo.repository.impl.EmployeeRepositoryDaoImpl;
import org.oocl.springdemo.repository.impl.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;
    @Resource(type = CompanyRepositoryDaoImpl.class)
    private CompanyRepository companyRepository;

    @Resource(type = EmployeeRepositoryDaoImpl.class)
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_all_employees_when_get_all_employees() throws Exception {
        Map<String, Integer> springIdMap = companyController.createCompany(new Company("spring"));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(springIdMap.get("id")))
                .andExpect(jsonPath("$[0].name").value("spring"));
    }

    @Test
    public void should_return_id_when_create_company_given_company() throws Exception {
        String requestBody = """
                {
                    "name": "spring"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_company_when_get_company_given_id() throws Exception {
        Employee employee = new Employee("Tom", 18, "Male", 5000.0);
        employeeRepository.create(employee);
        Map<String, Integer> companyIdMap = companyController.createCompany(new Company("spring"));
        mockMvc.perform(get("/companies/{id}", companyIdMap.get("id")))
                .andExpect(jsonPath("$.id").value(companyIdMap.get("id")))
                .andExpect(jsonPath("$.name").value("spring"))
                .andExpect(jsonPath("$.employees.length()").value(1));
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_true_when_update_company_given_id_and_company() throws Exception {
        String requestBody = """
                {
                    "name": "fall"
                }
                """;
        Map<String, Integer> companyIdMap = companyController.createCompany(new Company("spring"));
        mockMvc.perform(put("/companies/{id}", companyIdMap.get("id"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/companies/{id}", companyIdMap.get("id")))
                .andExpect(jsonPath("$.id").value(companyIdMap.get("id")))
                .andExpect(jsonPath("$.name").value("fall"));
    }

    @Test
    public void should_return_no_content_when_delete_company_given_id() throws Exception {
        companyController.createCompany(new Company("spring"));
        mockMvc.perform(delete("/companies/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_companies_when_get_companies_by_page_given_page_and_pageSize() throws Exception {
        Map<String, Integer> companyIdMap = companyController.createCompany(new Company("spring"));
        Map<String, Integer> companyIdMap2 = companyController.createCompany(new Company("fall"));
        mockMvc.perform(get("/companies?page={page}&pageSize={pageSize}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(companyIdMap.get("id")))
                .andExpect(jsonPath("$[0].name").value("spring"));
    }
}
