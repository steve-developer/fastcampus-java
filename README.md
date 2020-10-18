# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
***


#### 강의별 자료 링크 
* [01 개발 환경설정](https://github.com/steve-developer/fastcampus-java/tree/master/01-project-init)
* [03 REST API](https://github.com/steve-developer/fastcampus-java/tree/master/03-rest-api)
* [04 Lombok-JPA CRUD](https://github.com/steve-developer/fastcampus-java/tree/master/04-jpa-crud)
* [06 어드민 프로젝트 시작](https://github.com/steve-developer/fastcampus-java/tree/master/06-admin-init)
* [06 어드민 프로젝트 연관관계 설정](https://github.com/steve-developer/fastcampus-java/tree/master/06-jpa-join)
* [07 CRUD 인터페이스 정의와 ResponseBody 공통부 작성](https://github.com/steve-developer/fastcampus-java/tree/master/07-crud-controller)
* [08 사용자 API](https://github.com/steve-developer/fastcampus-java/tree/master/08-01-service-user-api)
* [08 상품 API](https://github.com/steve-developer/fastcampus-java/tree/master/08-02-service-item-api)
* [08 주문내역 API](https://github.com/steve-developer/fastcampus-java/tree/master/08-03-service-ordergroup-api)
* [09 JPA ENUM](https://github.com/steve-developer/fastcampus-java/tree/master/09-01-jpa-enum)
* [09 추상화적용하기](https://github.com/steve-developer/fastcampus-java/tree/master/09-02-refactoring)
* [10 샘플데이터 생성하기](https://github.com/steve-developer/fastcampus-java/tree/master/10-sample)
* [11 페이징처리](https://github.com/steve-developer/fastcampus-java/tree/master/11-paging)
* [12 화면처리하기](https://github.com/steve-developer/fastcampus-java/tree/master/12-view)

<br>

#### 변경점
* Java 버전 변경 
  - 기존 1.8 => 변경 11 [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
* Spring Boot 버전 변경
  - 기존 2.1.6 => 변경 2.3.4
  
* Lombok 사용법
* DI 부분 Autowired -> 생성자 주입 패턴으로 변경 
<br><br>


#### 부분 변경점 ( 기존대로 진행 하셔도 무방 합니다.)
  - application.properties 설정에서  => application.yaml 설정으로 변경 합니다.
  - application package가 com.example.study => com.fastcampus.java로 변경 합니다.
  - mySQL 설치 대신 docker 사용자들을 위한 docker-compose.yaml 제공
  

#### 변경된 프로젝트 생성 방법
* JDK 설치는 기존과 동일합니다. 버전만 11 버전으로 설치 하시면 됩니다. [Link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) <br>

<br>

#### DB를 포함한 쉬운 프로젝트 생성 방법 
#### JPA 미설정시 Application이 실행되지 않습니다. [04번째 강의 Lombok과 JPA] 부터 사용해주세요.)

* MVC만 적용한 프로젝트생성 [Link](https://github.com/steve-developer/fastcampus-java/tree/master/01-project-init) <br><br>
* MVC+ Lombok + JPA 프로젝트 생성 <br><br>
    1.  Spring Initializr 에서 Project SDK 를 JAVA 11버전을 선택 합니다. <br><br>
    <img src="/images/20201017_144659.png" width="1200" height="500"></img><br><br>
    2. 상세 설정에서 package name, type, language, packaging, java version 을 설정 합니다. <br><br>
    <img src="/01-project-init/images/20201017_231556.png" width="1200" height="500"></img><br><br>
    3. Dependencies 에서 Developer Tools -> Lombok 를 선택 <br><br>
    <img src="/images/20201017_144810.png" width="1200" height="500"></img><br><br>
    4. Dependencies 에서 Web -> Spring Web 를 선택 <br><br>
    <img src="/images/20201017_144834.png" width="1200" height="500"></img><br><br>
    5. Dependencies 에서 SQL -> Spring Data JPA, MySql Driver 를 선택 <br><br>
    <img src="/images/20201017_144958.png" width="1200" height="500"></img><br><br>
    <br><br>

#### 강의 자료 여는 방법
1. github에서 자료를 다운 로드 받습니다.<br>
    <img src="/images/20201018_143805.png" width="900" height="900"></img><br><br>
    
2. 적절한 곳에 압축을 해제 합니다.<br>
    <img src="/images/20201018_143914.png" width="800" height="700"></img><br><br>
    
3. Intellij 에서 File => Open 해당 폴더에서 열고자 하는 프로젝트의 build.gradle 파일을 선택 합니다.<br>
    <img src="/images/20201018_143940.png" width="400" height="700"></img><br><br>

4. open as project를 선택하여 진행하시면 됩니다.<br>
    <img src="/images/20201018_143951.png" width="400" height="200"></img><br><br> 