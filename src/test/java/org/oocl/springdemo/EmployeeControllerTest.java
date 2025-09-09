package org.oocl.springdemo;

import org.junit.jupiter.api.Test;
import org.oocl.springdemo.controller.EmployeeController;
import org.oocl.springdemo.pojo.Employee;
import org.springframework.beans.BeanUtils;
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
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;

    @Test
    public void should_return_id_when_create_employee_given_employee() throws Exception {
        String requestBody = """
                {
                    "name": "TOM",
                    "age": 18,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void should_return_employee_when_get_employee_given_id() throws Exception {
        String RequestBody = """
                {
                    "name": "TOM",
                    "age": 18,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        Employee employee = new Employee();
        employee.setName("TOM");
        employee.setAge(18);
        employee.setGender("Male");
        employee.setSalary(5000.0);
        employeeController.createEmployee(employee);
        mockMvc.perform(get("/employee/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("TOM"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(5000.0));
    }

    @Test
    public void should_return_employees_when_get_employees_given_gender() throws Exception {
        String RequestBody = """
                {
                    "id": 1,
                    "name": "TOM",
                    "age": 18,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        Employee employee = new Employee();
        employee.setName("TOM");
        employee.setAge(18);
        employee.setGender("Male");
        employee.setSalary(5000.0);
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        BeanUtils.copyProperties(employee, employee2);
        BeanUtils.copyProperties(employee, employee3);
        employee2.setGender("Female");
        employeeController.createEmployee(employee);
        employeeController.createEmployee(employee2);
        employeeController.createEmployee(employee3);
        mockMvc.perform(get("/employees?gender=Male")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TOM"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000.0))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[1].name").value("TOM"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(5000.0));
    }

    @Test
    public void should_return_employees_when_get_employees() throws Exception {
        String RequestBody = """
                {
                    "name": "TOM",
                    "age": 18,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        Employee employee = new Employee();
        employee.setName("TOM");
        employee.setAge(18);
        employee.setGender("Male");
        employee.setSalary(5000.0);
        Employee employee2 = new Employee();
        BeanUtils.copyProperties(employee, employee2);
        employee2.setGender("Female");
        employeeController.createEmployee(employee);
        employeeController.createEmployee(employee2);
        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TOM"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TOM"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Female"))
                .andExpect(jsonPath("$[1].salary").value(5000.0));
    }

    @Test
    public void should_return_true_when_update_employee_given_id_and_employee() throws Exception {
        String RequestBody = """
                {
                    "name": "Jack",
                    "age": 20,
                    "gender": "Female",
                    "salary": 50000.0
                }
                """;
        Employee employee = new Employee();
        employee.setName("TOM");
        employee.setAge(18);
        employee.setGender("Male");
        employee.setSalary(5000.0);
        employeeController.createEmployee(employee);
        mockMvc.perform(put("/employees/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
        mockMvc.perform(get("/employee/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jack"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }

    @Test
    public void should_return_no_content_when_delete_employees_given_id() throws Exception {
        String RequestBody = """
                {
                    "name": "Jack",
                    "age": 20,
                    "gender": "Female",
                    "salary": 50000.0
                }
                """;
        Employee employee = new Employee();
        employee.setName("TOM");
        employee.setAge(18);
        employee.setGender("Male");
        employee.setSalary(5000.0);
        employeeController.createEmployee(employee);
        mockMvc.perform(delete("/employees/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestBody))
                .andExpect(status().isNoContent());
    }

}
