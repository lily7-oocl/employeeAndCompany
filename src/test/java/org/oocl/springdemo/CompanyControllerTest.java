package org.oocl.springdemo;

import org.junit.jupiter.api.Test;
import org.oocl.springdemo.controller.CompanyController;
import org.oocl.springdemo.pojo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
        mockMvc.perform(get("/companies/{id}",1))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("fall"));
    }
}
