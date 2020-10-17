# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 해당 branch는 강의 [06-1.어드민 프로젝트 Entity 및 Repository 개발하기, 06-2. 어드민 프로젝트 Repository 테스트] 에 해당 합니다.
***

<br>

#### 동일한 부분
* Lombok annotation processing 설정 <br>
window : 왼쪽 상단 위 file -> settings -> build, Execution, Deveployment -> Compiler > Annotation Processors -> Enable annotation prossing 체크 <br><br>
macOS : 왼쪽 상단 위 IntelliJ IDEA -> Preferences ->  build, Execution, Deveployment -> Compiler > Annotation Processors -> Enable annotation prossing 체크 <br>
<img src="/06-admin-init/images/20201017_151448.png" width="800" height="700"></img>

* 또는 아래처럼 프로젝트 진행시에 오른쪽 하단 경고 창을 통해서 설정 할 수 있습니다.<br><br>
<img src="/06-admin-init/images/20201018_015920.png" width="1200" height="500"></img>

<br><br>

#### 필수 변경 점
1. Java 버전 변경 
    - 기존 1.8 => 변경 11 [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
2. Spring Boot 버전 변경
    - 기존 2.1.6 => 변경 2.3.4
  
3. gradle build 변경 ( MVC + JPA + Lombok 로 생성시 자동으로 동일해 집니다. [Link](https://github.com/steve-developer/fastcampus-java/tree/master) )
    ```
    변경전
    plugins {
        id 'org.springframework.boot' version '2.1.6.RELEASE'
        id 'java'
    }
    
    apply plugin: 'io.spring.dependency-management'
    
    group = 'com.example'
    version = '0.0.1-SNAPSHOT'
    sourceCompatibility = '1.8'
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
        compile('org.springframework.boot:spring-boot-starter-data-jpa')
        compile('mysql:mysql-connector-java')
        compile('org.projectlombok:lombok')
        implementation 'org.springframework.boot:spring-boot-starter-web'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
    }
 
    ```
   
    <br>
    
    ```
    변경후
    plugins {
        id 'org.springframework.boot' version '2.3.4.RELEASE'
        id 'io.spring.dependency-management' version '1.0.10.RELEASE'
        id 'java'
    }
    
    group = 'com.fastcampus'
    version = '0.0.1-SNAPSHOT'
    sourceCompatibility = '11'
    
    configurations {
        compileOnly {
            extendsFrom annotationProcessor
        }
    }
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-web'
        compileOnly 'org.projectlombok:lombok'
        runtimeOnly 'mysql:mysql-connector-java'
        annotationProcessor 'org.projectlombok:lombok'
        testImplementation('org.springframework.boot:spring-boot-starter-test') {
            exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
        }
    }
    
    test {
        useJUnitPlatform()
    }

    ```
3.  lombok 적용시 코드 변경 SearchParam Class
    ```
    @Data
    @AllArgsConstructor
    @NoArgsConstructor  // << 기본 생성자 추가
    public class SearchParam {
    
        private String account;
        private String email;
        private int page;
    }
    ```   

4.  5번째 강의에서 WorkBench로 Table 생성하는 부분 자동화로 변경 하였습니다.<br>
    잘 안되신다면 기존 강의대로 진행 해도 괜찮지만 해당 방법이 조금 더 편리하게 생성 가능 합니다.
    * src/main/java/resources/application.properties -> src/main/java/resources/application.yaml 확장자 변경 <br>
    * src/main/java/resources/application.yaml 에 아래 내용 처럼 jpa 관련 자동 실행 추가 <br><br>
    
    [before 예전 application.properties]
    ```
    # db source url
    spring.datasource.url=jdbc:mysql://localhost:3306/study?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
    
    # db response name
    spring.datasource.username=root
    
    # db response password
    spring.datasource.password=root
    
    spring.jpa.show-sql=true
    ```
    
    [after 변경된 application.yaml]
    ```
    spring:
      jpa:
        show-sql: true
      datasource:
        url: dbc:mysql://localhost:3306/study?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true # 새로 추가된 옵션 schema 가 없는 경우 생성
        username: root
        password: root
        driver-class-name: com.mysql.cj.jdbc.Driver
        schema: classpath:schema.sql  # 새로 추가된 옵션 src/main/resource/schema.sql
        initialization-mode: always   # 새로 추가된 옵션 위의 schema.sql 파일을 실행 시켜 준다.
    
    logging:
      level:
        root: info
        com.zaxxer.hikari.HikariConfig: debug
        org.hibernate.SQL: debug
        hibernate.type.descriptor.sql.BasicBinder: trace
    ```
    
    <br><br>
    
    잘 안되는 경우 새로 추가된 옵션을 제거 하고 아래의 순서대로 실행하여 MySQL SCHEMA 생성 <br><br>
    
    [자동실행이 잘 안되는 경우 새로 추가한 옵션 제거 application.yaml]
    ```
    spring:
      jpa:
        show-sql: true
      datasource:
        url: dbc:mysql://localhost:3306/study?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
        username: root
        password: root
        driver-class-name: com.mysql.cj.jdbc.Driver

    logging:
      level:
        root: info
        com.zaxxer.hikari.HikariConfig: debug
        org.hibernate.SQL: debug
        hibernate.type.descriptor.sql.BasicBinder: trace
    ```
    <br><br>
    
    1. MySQL Workbench로 접속 <br><br>
    <img src="/06-admin-init/images/20201018_014332.png" width="1200" height="500"></img><br><br>
    
    2. 왼쪽 상단 + sql 아이콘 클릭 sql 명령어 실행 <br><br>
    <img src="/06-admin-init/images/20201018_014309.png" width="1200" height="500"></img><br><br>
        ```
        -- MySQL Workbench Forward Engineering
        
        SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
        SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
        SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
        
        -- -----------------------------------------------------
        -- Schema study
        -- -----------------------------------------------------
        DROP SCHEMA IF EXISTS `study` ;
        
        -- -----------------------------------------------------
        -- Schema study
        -- -----------------------------------------------------
        CREATE SCHEMA IF NOT EXISTS `study` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
        USE `study` ;
        
        -- -----------------------------------------------------
        -- Table `study`.`admin_user`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`admin_user` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`admin_user` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'index',
          `account` VARCHAR(12) NOT NULL,
          `password` VARCHAR(50) NOT NULL,
          `status` VARCHAR(50) NOT NULL,
          `role` VARCHAR(50) NOT NULL,
          `last_login_at` DATETIME NULL DEFAULT NULL,
          `login_fail_count` INT(11) NULL DEFAULT NULL,
          `password_updated_at` datetime DEFAULT NULL,
          `registered_at` DATETIME NULL DEFAULT NULL,
          `unregistered_at` DATETIME NULL DEFAULT NULL,
          `created_at` DATETIME NOT NULL,
          `created_by` VARCHAR(20) NOT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        
        -- -----------------------------------------------------
        -- Table `study`.`category`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`category` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`category` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
          `type` VARCHAR(50) NOT NULL,
          `title` VARCHAR(100) NULL DEFAULT NULL,
          `created_at` DATETIME NOT NULL,
          `created_by` VARCHAR(20) NOT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        
        -- -----------------------------------------------------
        -- Table `study`.`partner`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`partner` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`partner` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
          `name` VARCHAR(100) NULL DEFAULT NULL,
          `status` VARCHAR(45) NULL DEFAULT NULL,
          `address` VARCHAR(100) NULL DEFAULT NULL,
          `call_center` VARCHAR(13) NULL DEFAULT NULL,
          `partner_number` VARCHAR(13) NULL DEFAULT NULL,
          `business_number` VARCHAR(16) NULL DEFAULT NULL,
          `ceo_name` VARCHAR(20) NULL DEFAULT NULL,
          `registered_at` DATETIME NULL DEFAULT NULL,
          `unregistered_at` DATETIME NULL DEFAULT NULL,
          `created_at` DATETIME NOT NULL,
          `created_by` VARCHAR(20) NOT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          `category_id` BIGINT(20) NOT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        
        -- -----------------------------------------------------
        -- Table `study`.`item`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`item` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`item` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
          `status` VARCHAR(50) NULL DEFAULT NULL,
          `name` VARCHAR(45) NOT NULL,
          `title` VARCHAR(100) NOT NULL,
          `content` TEXT NOT NULL,
          `price` DECIMAL(12,4) NOT NULL,
          `brand_name` VARCHAR(45) NULL DEFAULT NULL,
          `registered_at` VARCHAR(45) NULL DEFAULT NULL,
          `unregistered_at` DATETIME NULL DEFAULT NULL,
          `created_at` DATETIME NULL DEFAULT NULL,
          `created_by` VARCHAR(20) NULL DEFAULT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          `partner_id` BIGINT(20) NOT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        
        -- -----------------------------------------------------
        -- Table `study`.`user`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`user` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`user` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'index',
          `account` VARCHAR(12) NOT NULL,
          `password` VARCHAR(100) NOT NULL,
          `status` VARCHAR(50) NOT NULL,
          `email` VARCHAR(100) NULL DEFAULT NULL,
          `phone_number` VARCHAR(13) NOT NULL,
          `registered_at` DATETIME NULL DEFAULT NULL,
          `unregistered_at` DATETIME NULL DEFAULT NULL,
          `created_at` DATETIME NOT NULL,
          `created_by` VARCHAR(20) NOT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        
        -- -----------------------------------------------------
        -- Table `study`.`order_group`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`order_group` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`order_group` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
          `status` VARCHAR(50) NOT NULL,
          `order_type` VARCHAR(50) NOT NULL,
          `rev_address` VARCHAR(100) NULL DEFAULT NULL,
          `rev_name` VARCHAR(50) NULL DEFAULT NULL,
          `payment_type` VARCHAR(50) NULL DEFAULT NULL,
          `total_price` DECIMAL(12,4) NULL DEFAULT NULL,
          `total_quantity` INT(11) NULL DEFAULT NULL,
          `order_at` DATETIME NULL DEFAULT NULL,
          `arrival_date` DATETIME NULL DEFAULT NULL,
          `created_at` DATETIME NULL DEFAULT NULL,
          `created_by` VARCHAR(20) NULL DEFAULT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          `user_id` BIGINT(20) NOT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        
        -- -----------------------------------------------------
        -- Table `study`.`order_detail`
        -- -----------------------------------------------------
        DROP TABLE IF EXISTS `study`.`order_detail` ;
        
        CREATE TABLE IF NOT EXISTS `study`.`order_detail` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
          `status` VARCHAR(50) NOT NULL,
          `arrival_date` DATETIME NOT NULL,
          `quantity` INT(11) NULL DEFAULT NULL,
          `total_price` DECIMAL(12,4) NULL DEFAULT NULL,
          `created_at` DATETIME NOT NULL,
          `created_by` VARCHAR(20) NOT NULL,
          `updated_at` DATETIME NULL DEFAULT NULL,
          `updated_by` VARCHAR(20) NULL DEFAULT NULL,
          `order_group_id` BIGINT(20) NOT NULL,
          `item_id` BIGINT(20) NOT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_bin;
        
        SET SQL_MODE=@OLD_SQL_MODE;
        SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
        SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
        ```
    3. 왼쪽 상단 새로 고침 버튼을 눌러서 생성된 Schema 및 Table 확인 <br><br>
    <img src="/06-admin-init/images/20201018_014406.png" width="1200" height="500"></img><br><br>
    
    4. Application 실행하여 정상 적인 실행 확인 <br><br>
    <img src="/06-admin-init/images/20201018_015043.png" width="1200" height="500"></img><br><br>


#### JPA Test Code를 기존 @SpringBootTest를 쓰는 코드에서 JUnit5 @DataJpaTest 단위 테스트로 변경 하였습니다.
* 테스트 코드 JUnit5 로 변경 (필수) src.test.java.com.fastcampus.java package를 참고해주세요





#### JPA 단위 테스트 주요 내용 <br>

* 스프링 테스트 Default Auto-configuration : [Link](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)

* 스프링 부트 단위 테스트
    - 스프링에서 단위테스트는 여러가지가 있지만 그중에 대표적인것을 보면 다음과 같다. 너무 많아서 현 프로젝트에서 사용한 것만 설명하고 나머지는 [Link](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing) 참고해 주세요.<br>
    단위테스트는 보통 Method 레벨로 테스트가 이뤄지며, 하나의 기능이 잘 동작하는지를 검사 하는 테스트 이다. 그러므로 불필요한 Bean들을 로드할 필요가 없으며, 모두 로드 하는 경우 테스트의 시간만 길어지는 단점이 있다.
       
        | annotation | 의미 | Default Auto-configuration |
        |---|---| --- |
        |@WebMvcTest| MVC를 테스트 하기 위한 어노테이션, 간단한 Controller 에 대해서 테스트가 가능하다.| [Imported auto-configuration 참고](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)  |
        |@DataJpaTest| JPA 테스트 하기 위한 어노테이션, JPA관련만 로드 된다. |[Imported auto-configuration 참고](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)  |
        |@RestClientTest| REST Client 테스트 용도, RestTemplate과 같은 http client사용시 Mock Server를 만드는 용도 |[Imported auto-configuration 참고](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)  |

        
* 스프링 부트 통합 테스트
    - 스프링에서 통합 테스트를 위해서는 다음의 Annotation을 사용한다.<br>
    주로 controller 부터 service, repository, external library 등 모든 부분을 테스트하며, 스프링의 모든 Bean을 로드하여 사용하므로 테스트의 시간이 길다.
    <br>
        
    | annotation | 의미 | 특이사항 |
    |---|---| --- |
    |@SpringBootTest| 스프링의 실행 부터 모든 bean을 로드하여, 처음부터 끝까지 모두 테스트 가능 | |
    <br/>
       
* 그외 사용하는 Annotation
    <br>
    
    | annotation | 의미 | 특이사항 |
    |---|---| --- |
    |@Import| 단위테스트를 할때, 별도로 만든 Bean, Component 등 필요한 Bean Load | |
    |@AutoConfigureTestDatabase| 테스트시 실제 DB를 사용하거나 Memory DB를 사용할때 사용 | ex) @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) |
    |@DisplayName| 테스트의 이름을 지정 | |
    |@Test| 테스트할 Method 지정 | |
    |@Transactional| 테스트가 끝나면 해당 DB의 내용을 ROLL BACK할때 사용한다. | DB의 AUTO Increment index의 숫자는 계속 증가 한다. |
    |@TestConfiguration| 테스트에서 사용할 설정을 할때 class 에 적용 한다. ||

* Junit5
    - org.springframework.util Assert 대신에 org.junit.jupiter.api 에 있는 Assertions 사용    
    
    
#### Example
```
@DataJpaTest                                                                    // JPA 테스트 관련 컴포넌트만 Import
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 db 사용
@DisplayName("AdminUser Repository 테스트")
public class AdminUserRepositoryTest {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    @DisplayName("AdminUser Create 테스트")
    public void create(){
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount("AdminUser01");
        adminUser.setPassword("AdminUser01");
        adminUser.setStatus("REGISTERED");
        adminUser.setRole("PARTNER");
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setCreatedBy("AdminServer");

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        Assertions.assertNotNull(newAdminUser);
    }
}
```
