package org.oocl.springdemo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oocl.springdemo.controller.EmployeeController;
import org.oocl.springdemo.entity.pojo.Company;
import org.oocl.springdemo.repository.CompanyRepository;
import org.oocl.springdemo.repository.EmployeeRepository;
import org.oocl.springdemo.repository.impl.CompanyRepositoryDaoImpl;
import org.oocl.springdemo.repository.impl.EmployeeRepositoryDaoImpl;
import org.oocl.springdemo.entity.pojo.Employee;
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
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;
    @Resource(type = EmployeeRepositoryDaoImpl.class)
//    @Resource(type = EmployeeRepositoryImpl.class)
    private EmployeeRepository employeeRepository;
    @Resource(type = CompanyRepositoryDaoImpl.class)
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        companyRepository.add(new Company("big smart"));
    }
    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_id_when_create_employee_given_employee() throws Exception {
        String requestBody = """
                {
                    "name": "TOM",
                    "age": 18,
                    "gender": "Male",
                    "salary": 5000.0,
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_bad_request_when_create_employee_given_employee_that_has_invalid_age_that_not_in_range_between_18_and_65() throws Exception {
        String requestBody = """
                {
                    "name": "TOM",
                    "age": 17,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        String requestBody2 = """
                {
                    "name": "TOM",
                    "age": 66,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_when_create_employee_given_employee_that_has_over_and_inclusive_30_age_and_salary_below_20000() throws Exception {
        String requestBody = """
                {
                    "name": "TOM",
                    "age": 40,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_employee_when_get_employee_given_id() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        mockMvc.perform(get("/employees/{id}", employeeIdMap.get("id")))
                .andExpect(jsonPath("$.id").value(employeeIdMap.get("id")))
                .andExpect(jsonPath("$.name").value("TOM"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(5000.0));
    }

    @Test
    public void should_return_bad_request_when_get_employee_that_is_null_given_id() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        mockMvc.perform(get("/employees/{id}", employeeIdMap.get("id")+1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_employees_when_get_employees_given_gender() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        employeeController.createEmployee(new Employee("TOM", 18, "Female", 5000.0));
        Map<String, Integer> employeeIdMap2 = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        mockMvc.perform(get("/employees?gender=Male"))
                .andExpect(jsonPath("$[0].id").value(employeeIdMap.get("id")))
                .andExpect(jsonPath("$[0].name").value("TOM"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000.0))
                .andExpect(jsonPath("$[1].id").value(employeeIdMap2.get("id")))
                .andExpect(jsonPath("$[1].name").value("TOM"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(5000.0));
    }

    @Test
    public void should_return_employees_when_get_employees() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        Map<String, Integer> employeeIdMap2 = employeeController.createEmployee(new Employee("TOM", 18, "Female", 5000.0));
        mockMvc.perform(get("/employees"))
                .andExpect(jsonPath("$[0].id").value(employeeIdMap.get("id")))
                .andExpect(jsonPath("$[0].name").value("TOM"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000.0))
                .andExpect(jsonPath("$[1].id").value(employeeIdMap2.get("id")))
                .andExpect(jsonPath("$[1].name").value("TOM"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Female"))
                .andExpect(jsonPath("$[1].salary").value(5000.0));
    }

    @Test
    public void should_return_no_content_when_update_employee_given_exist_id_and_new_employee() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        String RequestBody = """
                {
                    "id": %d,
                    "name": "Jack",
                    "age": 20,
                    "gender": "Female",
                    "salary": 50000.0,
                    "status": true
                }
                """.formatted(employeeIdMap.get("id"));
        mockMvc.perform(put("/employees/{id}", employeeIdMap.get("id"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/employees/{id}", employeeIdMap.get("id"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(jsonPath("$.id").value(employeeIdMap.get("id")))
                .andExpect(jsonPath("$.name").value("Jack"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }

    @Test
    public void should_return_bad_request_when_update_employee_given_exist_id_that_is_deleted_and_new_employee() throws Exception {
        String RequestBody = """
                {
                    "name": "Jack",
                    "age": 20,
                    "gender": "Female",
                    "salary": 50000.0
                }
                """;
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        employeeController.deleteEmployeeById(employeeIdMap.get("id"));
        mockMvc.perform(put("/employees/{id}", employeeIdMap.get("id"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_when_update_employee_given_not_exist_id_and_new_employee() throws Exception {
        String RequestBody = """
                {
                    "name": "Jack",
                    "age": 20,
                    "gender": "Female",
                    "salary": 50000.0
                }
                """;
        mockMvc.perform(put("/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_no_content_when_delete_employees_by_id() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        mockMvc.perform(delete("/employees/{id}", employeeIdMap.get("id")))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_bad_request_when_delete_employees_by_not_exist_id() throws Exception {
        mockMvc.perform(delete("/employees/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_when_delete_employees_by_already_deleted_employee_id() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0, false));
        employeeController.deleteEmployeeById(employeeIdMap.get("id"));
        mockMvc.perform(delete("/employees/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_employees_when_get_employees_by_page() throws Exception {
        Map<String, Integer> employeeIdMap = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        Map<String, Integer> employeeIdMap2 = employeeController.createEmployee(new Employee("TOM", 18, "Male", 5000.0));
        mockMvc.perform(get("/employees?page=1&pageSize=2"))
                .andExpect(jsonPath("$[0].id").value(employeeIdMap.get("id")))
                .andExpect(jsonPath("$[0].name").value("TOM"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000.0))
                .andExpect(jsonPath("$[1].id").value(employeeIdMap2.get("id")))
                .andExpect(jsonPath("$[1].name").value("TOM"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(5000.0));
    }

}
