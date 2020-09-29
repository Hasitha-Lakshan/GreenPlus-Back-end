# GreenPlus-Back-end
This repository is created for the development of the back-end of a web-based application called GreenPlus.

### Prerequisites
* Install Spring Tools 4 for Eclipse.
* Install MySQL Workbench.
* Install Postman App.

### Installing
1. Clone the respository:
    ```
   git clone https://github.com/Hasitha-Lakshan/GreenPlus-Back-end.git
    ```

2. Dependencies:
   * Spring Web
   * Spring Data JPA
   * Rest Repositories
   * MYSQL Driver
   * Spring Security
   * Lombok

3. Configuration of application.properties:
   * Connect to the database:
   ```
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
   spring.datasource.username=springuser
   spring.datasource.password=ThePassword
   ```
4. Run the application as Spring Boot App

## Built With
* Core Java
* Spring Data REST
* Java Persistence API
* Java Database Connectivity
* Apache Maven
* JSON Web Tokens
* Java KeyStore
* MySQL

## Authors
See the detailed list of [contributors](https://github.com/Hasitha-Lakshan/GreenPlus-Back-end/graphs/contributors) who contributed to this project.
