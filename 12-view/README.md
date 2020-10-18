# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 강의 [화면처리 VIEW] 에 해당 합니다.
***

<br>

#### 동일한 부분
* Lombok annotation processing 설정 <br>
window : 왼쪽 상단 위 file -> settings -> build, Execution, Deveployment -> Compiler > Annotation Processors -> Enable annotation prossing 체크 <br><br>
macOS : 왼쪽 상단 위 IntelliJ IDEA -> Preferences ->  build, Execution, Deveployment -> Compiler > Annotation Processors -> Enable annotation prossing 체크 <br>
<img src="/12-view/images/20201017_151448.png" width="800" height="700"></img>

* 또는 아래처럼 프로젝트 진행시에 오른쪽 하단 경고 창을 통해서 설정 할 수 있습니다.<br><br>
<img src="/12-view/images/20201018_015920.png" width="1200" height="500"></img>

<br><br>

#### 필수 변경 점
1. Java 버전 변경 
    - 기존 1.8 => 변경 11 [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
2. Spring Boot 버전 변경
    - 기존 2.1.6 => 변경 2.3.4

3. @Autowired 제거 생성자 패턴으로 변경

4. UserApiController getMapping 주소 변경
    ```
    변경전
    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    @RequiredArgsConstructor
    public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {
    
        private final UserApiLogicService userApiLogicService;
    
        @GetMapping("/{id}/orderInfo")
        public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id){
            return userApiLogicService.orderInfo(id);
        }
    
        @GetMapping("/search") // << 변경할 부분
        public Header<List<UserApiResponse>> findAll(@PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable){
            log.info("{}",pageable);
            return userApiLogicService.search(pageable);
        }
    }
    
    ```
   
    ```
    변경후
    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    @RequiredArgsConstructor
    public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {
    
        private final UserApiLogicService userApiLogicService;
    
        @GetMapping("/{id}/orderInfo")
        public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id){
            return userApiLogicService.orderInfo(id);
        }
    
        @GetMapping("")
        public Header<List<UserApiResponse>> findAll(@PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable){
            log.info("{}",pageable);
            return userApiLogicService.search(pageable);
        }
    }
    ```

5. view_file 폴더에 있는 static , templates 폴더를 src / main /  resources 로 복사
    <img src="/12-view/images/20201018_141142.png" width="500" height="700"></img>
    
6. config/ObjectMapperConfig 에서 SNAKE_CASE 로 변경 하도록 설정
    ```
    @Configuration
    public class ObjectMapperConfig {
    
        @Bean
        public ObjectMapper objectMapper(){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); // << SNAKE_CASE로 동작
            return mapper;
        }
    }
    ```

7. web broswer로 실행하여 확인
    <img src="/12-view/images/20201018_141708.png" width="1200" height="500"></img>
   