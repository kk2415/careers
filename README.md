# Overview
다양한 개발자 채용공고 사이트에서 공고 데이터를 크롤링 후 한 눈에 볼 수 있게 제공합니다.   

---

# Getting Started
Quickly executes only job modules without crawler modules
+ Clone Project
  ```shell
    git clone https://github.com/kk2415/careers.git
  ```
+ Required settings
  + enter datasource information in .yaml file
    ```yaml
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/{database}?serverTimezone=Asia/Seoul
        username: {username}
        password: {password}
    ```
  + execute DDL query\
    execute Table CREATE query in resource/schema.sql file
+ Build And Start
  ```shell
    gradlew :job:clean build
  ```
  ```shell
    java -jar .\job\build\libs\job-1.0.0.jar --jasypt.encryptor.password={encryption_key}
  ```

---

# Development Environment
+ Java(OpenJDK 17)
+ Spring Boot, Spring Data JPA, Spring Security
+ MySQL(8.0.26)
