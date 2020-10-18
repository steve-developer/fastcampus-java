# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 강의 [Refactoring] 에 해당 합니다.
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

3. Service @Autowired 받던 부분 생성자 패턴의 Lombok 적용
    ```
    변경전
    @Service
    public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse,OrderGroup> {
    
        @Autowired
        private UserRepository userRepository;
    ```
   
    ```
    변경후
    @Service
    @RequiredArgsConstructor
    public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {
    
        private final UserRepository userRepository;
    ```

4. UserApiController, UserApiLogicService.  Refactoring 적용 안된 부분 적용
    ```
    변경전
    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {
    
        @Autowired
        private UserApiLogicService userApiLogicService;
    
        @Override
        @PostMapping("")    // /api/user
        public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
            log.info("{}",request);
            return userApiLogicService.create(request);
        }
    
        @Override
        @GetMapping("{id}") // /api/user/{id}
        public Header<UserApiResponse> read(@PathVariable(name = "id") Long id) {
            log.info("read id : {}",id);
            return userApiLogicService.read(id);
        }
    
        @Override
        @PutMapping("") // /api/user
        public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {
            return userApiLogicService.update(request);
        }
    
        @Override
        @DeleteMapping("{id}")  // /api/user/{id}
        public Header delete(@PathVariable Long id) {
            log.info("delete : {}",id);
            return userApiLogicService.delete(id);
        }
    }
    ```
   
    ```
    변경후
    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {
    
    }
    ```
   
    ```
    변경전
    @Service
    public class UserApiLogicService implements CrudInterface<UserApiRequest , UserApiResponse> {
    
        @Autowired
        private UserRepository userRepository;
        ...
        ...
    ```
    
    ```
    변경후
    @Service
    public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {
    ...
    ...
    ```
    