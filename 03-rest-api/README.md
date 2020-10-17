# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 해당 branch는 강의 [03.Spring Boot와 REST API] 에 해당 합니다.
***

<br>

#### 필수 변경점
* Java 버전 변경 
  - 기존 1.8 => 변경 11 [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
* Spring Boot 버전 변경
  - 기존 2.1.6 => 변경 2.3.4

* MVC Controller Test Code 추가
<br><br>


#### 부분 변경점 ( 기존대로 진행 하셔도 무방 합니다.)
  - application.properties 설정에서  => application.yaml 설정으로 변경 합니다.
  - application package가 com.example.study => com.fastcampus.java로 변경 합니다.

<br>  

#### 변경된 프로젝트 생성 방법
* JDK 설치는 기존과 동일합니다. 버전만 11 버전으로 설치 하시면 됩니다. [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) <br>
* 프로젝트 생성 <br><br>
    1. Spring Initializr 에서 Project SDK 를 JAVA 11버전을 선택 합니다. <br><br>
    <img src="/01-project-init/images/20201017_144659.png" width="1200" height="500"></img><br><br>
    2. 상세 설정에서 package name, type, language, packaging, java version 을 설정 합니다. <br><br>
    <img src="/01-project-init/images/20201017_231556.png" width="1200" height="500"></img><br><br>
    3. Dependencies 에서 Web -> Spring Web 를 선택 <br><br>
    <img src="/01-project-init/images/20201017_231617.png" width="1200" height="500"></img><br><br>
    <br>

#### 업데이트 된 build.gradle 파일

[before 이전 버전]
```
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
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}


```


[after Renewal 버전]
```
plugins {
    id 'org.springframework.boot' version '2.3.4.RELEASE'
    id 'io.spring.dependency-management' version '1.0.10.RELEASE'
    id 'java'
}

group = 'com.fastcampus'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
}

test {
    useJUnitPlatform()
}
```

<br><br>

#### Controller 주소 노출 관련 Annotation
| annotation | 의미 | 특이사항 |
|---|---| --- |
|@Autowired| Spring Boot 과 관리하는 Bean을 주입 받기 위한 설정 ||
|@RestController| 적용된 Class가 REST API 로 동작 하도록 설정 | Response 가 객체인 경우 대해서 자동으로 object mapper 적용 |
|@Controller| 적용된 Class가 View (Html) 를 내리도록 설정 | 별도의 적용이 없으면 resource 하위에 html page를 참고 합니다. |
|@RequestMapping| 외부에 주소를 노출 하는 설정 | @RestController, @Controller, HTTP GET, POST, PUT, DELETE 등 적용 가능. method를 지정하지 않으면 전부 동작 |
|@GetMapping| RequestMapping method를 분리한 하위 개념으로 Get Method만 동작 한다. ||       
|@PostMapping| RequestMapping method를 분리한 하위 개념으로 Post Method만 동작 한다. ||
|@PutMapping| RequestMapping method를 분리한 하위 개념으로 Put Method만 동작 한다. ||
|@DeleteMapping| RequestMapping method를 분리한 하위 개념으로 Delete Method만 동작 한다. ||
|@ResponseBody| @Controller Annotation이 붙은 class에서 Json 응답을 내릴때 사용 ||

<br><br>

#### Controller 데이터 맵핑 Annotation
| annotation | 의미 | 특이사항 |
|---|---| --- |
|@RequestParam| URL 의 Query Parameter 를 Parsing 할때 사용 | url : https://foo.co.kr?id=1 , ex) public Student create(@RequestParam Long id) |
|Object| Query Parameter를 Object로 바로 맵핑할때 사용 | https://foo.co.kr?id=1 , ex) public Student create(Student student) |
|@PathVariable| URL 의 Path의 값을 Parsing 할때 사용 | https://foo.co.kr/1 , ex) @GetMapping("/{id}" public Optional<Student> read(@PathVariable Long id) |
|@RequestBody| Http Method의 POST, PUT의 BODY를 Parsing할때 사용 | https://foo.co.kr |

<br><br>

#### JUnit 테스트 src.test.java.com.fastcampus.java.controller 에 있습니다. ( TODO로 작성해야하는 테스트 코드가 있습니다. )
1. GetControllerTest
    ```
    @WebMvcTest(GetController.class)        // WebMvc Test Annotation ( GetController 를 테스트)
    @AutoConfigureMockMvc                   // MockMvc 자동 설정 Annotation
    public class GetControllerTest {
    
        @Autowired
        private MockMvc mockMvc;            // AutoConfigureMockMvc 으로 자동 주입된 mockMvc
    
    
        @Test                               // JUnit Test 설정 Annotation
        @DisplayName("getRequest 테스트")    // 테스트명 미적용시 메소드 이름
        public void getRequestTest() throws Exception {
            // given
            URI uri = UriComponentsBuilder.newInstance()
                    .path("/api/getMethod")
                    .build()
                    .toUri();
    
            // when
            mockMvc.perform(get(uri))
    
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("Hi getMethod"))
                .andDo(print());
        }
    }
    ```
    <br><br>
2. PostControllerTest
    ```
    @Test                               // JUnit Test 설정 Annotation
    @DisplayName("postMethod 테스트")    // 테스트명 미적용시 메소드 이름
    public void postMethodTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // given
        URI uri = UriComponentsBuilder.newInstance()
                .path("/api/postMethod")
                .build()
                .toUri();

        var searchParam = new SearchParam();
        searchParam.setAccount("account");
        searchParam.setEmail("email");
        searchParam.setPage(10);

        // when
        mockMvc.perform(post(uri)  // post 로 테스트
                .content(objectMapper.writeValueAsString(searchParam))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.account").value("account")) // $.을 통하여 json 에 접근 가능
            .andExpect(jsonPath("$.email").value("email")) // $.을 통하여 json 에 접근 가능
            .andExpect(jsonPath("$.page").value("10")) // $.을 통하여 json 에 접근 가능
            .andDo(print());
    }
    ```
    <br>
    
* 스프링 테스트 Default Auto-configuration : [Link](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)

* 스프링 부트 단위 테스트
    - 스프링에서 단위테스트는 여러가지가 있지만 그중에 대표적인것을 보면 다음과 같다. 너무 많아서 현 프로젝트에서 사용한 것만 설명하고 나머지는 [Link](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing) 참고해 주세요.<br>
    단위테스트는 보통 Method 레벨로 테스트가 이뤄지며, 하나의 기능이 잘 동작하는지를 검사 하는 테스트 이다. 그러므로 불필요한 Bean들을 로드할 필요가 없으며, 모두 로드 하는 경우 테스트의 시간만 길어지는 단점이 있다.
       
    | annotation | 의미 | Default Auto-configuration |
    |---|---| --- |
    |@WebMvcTest| MVC를 테스트 하기 위한 어노테이션, 간단한 Controller 에 대해서 테스트가 가능하다.| [Imported auto-configuration 참고](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)  |
    |@DataJpaTest| JPA 테스트 하기 위한 어노테이션, JPA관련만 로드 된다. |[Imported auto-configuration 참고](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)  |
    |@RestClientTest| REST Client 테스트 용도, RestTemplate과 같은 http client사용시 Mock Server를 만드는 용도 |[Imported auto-configuration 참고](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html#test-auto-configuration)  |
    <br>
        
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

<br><br>

* 테스트에 주로 사용되는 Class
    <br>
    
    | Class | Method | 내용 | 특이사항 |
    |---|---|---|---|
    |org.mockito.BDDMockito| given | MockBean에 대해서 in/out object를 설정 하여 mocking을 제공 | 반드시 input값이 같아야 원하는 결과를 return 한다. |
    | MockMvc | - | REST API 요청을 mocking 할때 사용  | |   
    | MockMvc | perform | REST API 요청에 필요한 한 RequestBuilder 를 받는다.  | |   
    | RequestBuilder | post | POST API 요청 생성 파라미터로 uri 를 받는다.  | |   
    | RequestBuilder | put | PUT API 요청 생성 파라미터로 uri 를 받는다.  | |   
    | RequestBuilder | get | GET API 요청 생성 파라미터로 uri 를 받는다.  | |   
    | RequestBuilder | delete | DELETE API 요청 생성 파라미터로 uri 를 받는다.  | |   
    | MockHttpServletRequestBuilder | content | POST Body의 내용이 들어 간다.  | Get요청의 경우 넣지 않는다. |   
    | MockHttpServletRequestBuilder | contentType | POST Body의 contentType이 들어 간다.  | |   
    | MockHttpServletRequestBuilder | accept | POST API의 accept 값이 들어 간다.  | |   \
    | ResultActions | andExpect | 결과에 대한 기대값을 설정 한다. |
    | ResultActions | andDo | 결과에 대한 추가 설정값을 설정한다. ex) print() 로 출력 한다. |
    | MockMvcResultMatchers | status() | andExpect() 의 결과에 대한 값을 넣으며, status, json data등 결과 기대값에 대해서 상세 값을 넣는다. isOK() 등 여러 값이 있다. |
    | MockMvcResultMatchers | jsonPath() | JSON 응답에 대한 결과 값을 넣는다. $.으로 JSON Value 에 접근 할 수 있다.  깊이 및 배열의 경우 $.result.status[0] 으로 접근 가능 |
    <br>
    
#### 테스트 Class 및 Method의 경우 같은 이름의 메소드(ex: status(), print(), post() etc... )를 가진 클래스가 무수히 많음으로 다음의 Class를 참고 하시면 됩니다..
```
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
```