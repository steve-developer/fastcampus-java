# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 강의 [서비스 로직 사용자 API] 에 해당 합니다.
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

3. ObjectMapperConfig 추가 LocalDateTime 직렬화/역직렬화 관련 내용 추가 [Link](https://reflectoring.io/configuring-localdate-serialization-spring-boot/)
    ```
    @Configuration
    public class ObjectMapperConfig {
    
        @Bean
        public ObjectMapper objectMapper(){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper;
        }
    }

    ```

4. @Autowired 로 주입 받던 방식을 Lombok를 사용한 생성자 주입 방식으로 변경
    ```
    대상 class : UserApiController, UserApiLogicService
    ```
    
    ```
    변경전
    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {
    
        @Autowired
        private UserApiLogicService userApiLogicService;
        ...
        ...
    ```
    ```
    변경후
    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    @RequiredArgsConstructor    // lombok 사용
    public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {
        // final로 선언
        private final UserApiLogicService userApiLogicService;
    ```

5.  UserApiController 단위 테스트 샘플 추가
    ```
    @WebMvcTest(UserApiController.class)        // WebMvc Test Annotation ( UserApiController 를 테스트)
    @AutoConfigureMockMvc                       // MockMvc 자동 설정 Annotation
    @Import(ObjectMapperConfig.class)
    public class UserApiControllerUnitTest {
    
        @Autowired
        private MockMvc mockMvc;            // AutoConfigureMockMvc 으로 자동 주입된 mockMvc
    
        @MockBean
        private UserApiLogicService userApiLogicService;
    
        @Autowired
        private ObjectMapper objectMapper;
    
        @Test
        @DisplayName("사용자 생성 테스트")
        public void createTest() throws Exception {
            // given
            URI uri = UriComponentsBuilder.newInstance()
                    .path("/api/user")
                    .build()
                    .toUri();
    
            var now = LocalDateTime.now();
            var mockReq = new Header<UserApiRequest>();
            var data = new UserApiRequest();
            data.setAccount("userAccount");
            data.setEmail("userAccount@gmail.com");
            data.setPassword("password");
            data.setPhoneNumber("010-1111-2222");
            data.setRegisteredAt(now);
            data.setStatus("REGISTERED");
            mockReq.setData(data);
    
            var mockRes = new Header<UserApiResponse>();
            var resData = new UserApiResponse();
            resData.setAccount("userAccount");
            resData.setEmail("userAccount@gmail.com");
            resData.setPassword("password");
            resData.setPhoneNumber("010-1111-2222");
            resData.setRegisteredAt(now);
            resData.setStatus("REGISTERED");
            mockRes.setData(resData);
    
            // userApiLogicService mocking
            given(userApiLogicService.create(mockReq)).willReturn(mockRes);
    
    
            // when
            mockMvc.perform(post(uri)  // post 로 테스트
                    .content(objectMapper.writeValueAsString(mockReq))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
    
                    // then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.account").value("userAccount")) // $.을 통하여 json 에 접근 가능
                    .andExpect(jsonPath("$.data.email").value("userAccount@gmail.com")) // $.을 통하여 json 에 접근 가능
                    .andExpect(jsonPath("$.data.status").value("REGISTERED")) // $.을 통하여 json 에 접근 가능
                    .andDo(print());
        }
    }
    ```

6.  gradle build 변경 ( MVC + JPA + Lombok 로 생성시 자동으로 동일해 집니다. [Link](https://github.com/steve-developer/fastcampus-java/tree/master) )
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
7.  lombok 적용시 코드 변경 SearchParam Class
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
    
8. Lombok
    <br>
   
    | annotation | 의미 | 특이사항 |
    |---|---| --- |
    |@NoArgsConstructor| 매개변수가 없는 기본 생성자 사용 ||
    |@AllArgsConstructor| 모든 매개변수의 생성자 사용 ||
    |@RequiredArgsConstructor|초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성| 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용 |
    |@Builder| 빌더 패턴을 사용할때 사용 ||
    |@Accessors(chain = true)| 체이닝 패턴을 사용할때 사용 ||
    |@Getter| Getter 메소드 생성 ||
    |@Setter| Setter 메소드 생성 ||
    |@Data| @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor 모두 포함 | JPA 에서 OneToMany, ManyToOne 같은 부분에 사용하면 toString()에서 문제 발생 @Exclude를 사용해서 제외 하거나 다른 방법으로 설정|
    <br>   
    
        
    