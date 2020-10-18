# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 강의 [샘플 데이터 생성하기] 에 해당 합니다.
***

<br>

#### 동일한 부분
* Lombok annotation processing 설정 <br>
window : 왼쪽 상단 위 file -> settings -> build, Execution, Deveployment -> Compiler > Annotation Processors -> Enable annotation prossing 체크 <br><br>
macOS : 왼쪽 상단 위 IntelliJ IDEA -> Preferences ->  build, Execution, Deveployment -> Compiler > Annotation Processors -> Enable annotation prossing 체크 <br>
<img src="/10-sample/images/20201017_151448.png" width="800" height="700"></img>

* 또는 아래처럼 프로젝트 진행시에 오른쪽 하단 경고 창을 통해서 설정 할 수 있습니다.<br><br>
<img src="/10-sample/images/20201018_015920.png" width="1200" height="500"></img>

<br><br>

#### 필수 변경 점
1. Java 버전 변경 
    - 기존 1.8 => 변경 11 [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
2. Spring Boot 버전 변경
    - 기존 2.1.6 => 변경 2.3.4

3. JPA Test Code 를 통한 생성 부분 수정
    ```
    Annotation 변경
    @DataJpaTest                                                                    // JPA 테스트 관련 컴포넌트만 Import
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 db 사용
    @DisplayName("CategorySample 생성")
    @Import({JpaConfig.class, LoginUserAuditorAware.class})
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // DataJpaTest는 기본이 rollback 이므로 이를 막기 위한 설정
    ```

4. 강의속 샘플 데이터는 /sql-data 안에 들어 있습니다. 강의 처럼 workbench 로 하셔도 됩니다.
    <img src="/10-sample/images/20201018_130728.png" width="500" height="500"></img>
        

5.  schema.sql , data.sql 을 통한 application 실행시 자동 적용 되도록 변경<br>
    schema.sql 은 테이블이 없을때만 sql 명령어로 create table을 하므로, 한번 생성하면 유지됩니다.<br>
    data.sql 내부에는 sql명령어로 drop table 을 하므로, application 재실행 하면 데이터가 초기화 됩니다. <br>
    ```
    spring:
      jpa:
        show-sql: true
    
      datasource:
        url: jdbc:mysql://localhost:3306/study?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true # 새로 추가된 옵션 schema 가 없는 경우 생성
        username: root
        password: root
        driver-class-name: com.mysql.cj.jdbc.Driver
        schema: classpath:schema.sql  # 새로 추가된 옵션 src/main/resource/schema.sql
        data: classpath:data.sql      # 새로 추가된 옵션, src/main/resource/data.sql 에 있는 내용 실행
        initialization-mode: always   # 새로 추가된 옵션 위의 schema.sql 파일을 실행 시켜 준다.
    
    
    logging:
      level:
        root: info
        com.zaxxer.hikari.HikariConfig: debug
        org.hibernate.SQL: debug
        hibernate.type.descriptor.sql.BasicBinder: trace
    ```
    <br>
       
    schema.sql , data.sql 위치<br>
    <img src="/10-sample/images/20201018_131359.png" width="350" height="500"></img>
    <br>
    
    Application 실행 후 DB 확인<br>
    <img src="/10-sample/images/20201018_131407.png" width="1200" height="500"></img>