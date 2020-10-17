# FastCampus Java 과정 Renewal

#### 기존에 제공 되어 졌었던 패스트 캠퍼스 어드민 개발을 현재의 버전에 맞게 Renewal 합니다.
#### 해당 branch는 강의 [01.개발환경 설치] 에 해당 합니다.
***

<br>

#### 필수 변경점
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
