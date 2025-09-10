package org.oocl.springdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oocl.springdemo.controller.CompanyController;
import org.oocl.springdemo.dao.CompanyDao;
import org.oocl.springdemo.pojo.Company;
import org.oocl.springdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    @Autowired
    private CompanyDao companyDao;

    @BeforeEach
    void setUp() {
        companyDao.deleteAll();
    }

    @Test
    public void should_return_all_employees_when_get_all_employees() throws Exception {
        companyController.createCompany(new Company("spring"));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void should_return_company_when_get_company_given_id() throws Exception {
        companyController.createCompany(new Company("spring"));
        mockMvc.perform(get("/companies/{id}",1))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("spring"));
    }

    @Test
    public void should_return_true_when_update_company_given_id_and_company() throws Exception {
        String requestBody = """
                {
                    "name": "fall"
                }
                """;
        companyController.createCompany(new Company("spring"));
        mockMvc.perform(put("/companies/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/companies/{id}",1))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("fall"));
    }

    @Test
    public void should_return_no_content_when_delete_company_given_id() throws Exception {
        companyController.createCompany(new Company("spring"));
        mockMvc.perform(delete("/companies/{id}",1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_companies_when_get_companies_by_page_given_page_and_pageSize() throws Exception {
        companyController.createCompany(new Company("spring"));
        companyController.createCompany(new Company("fall"));
        mockMvc.perform(get("/companies?page={page}&pageSize={pageSize}",1,1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("spring"));
    }
}
