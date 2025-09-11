CREATE TABLE employee (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          age INT NOT NULL,
                          gender VARCHAR(10) NOT NULL,
                          salary DOUBLE NOT NULL,
                          status BOOLEAN DEFAULT FALSE,
                          company_id INT,
                          FOREIGN KEY (company_id) REFERENCES company(id)
);