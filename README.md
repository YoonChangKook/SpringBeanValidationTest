# validation-test
* spring 5.1.7
* jdk: 1.8

### 개요
bean validation을 이용한 controller 파라미터 검증 테스트 프로젝트

org.springframework.validation의 Validator 인터페이스를 통한 객체 검증부터 hibernate bean validator를 이용한 객체 검증까지의 테스트를 다룬다.

### 테스트
Spring 3.2부터 도입된 MockMvc를 이용하여 controller에 요청을 보내 테스트한다.