## 예약배송 플랫폼 포만감 v3, REST API 서버

<br/>

### 사용기술
- Application
    - Spring Boot/Security/OAuth2/Ehcache
    - JPA/QueryDSL
    - RabbitMQ
    - Grafana, Prometheus

- Operation
    - GCE(GCP)/GCR
    - Docker
    - Github Actions
   
<br/>   
    
### 개요
- 모노리스 REST API 서버.

<br/>

### 프로젝트 아키텍처 도식화
|Server Architecture|
|--|
|![sys](https://github.com/cholnh/pomangam-api-monilith/blob/master/assets/images/architecture.png)|

<br/>

### 코드 스타일

- [아키텍처 및 Directory 스타일 가이드](https://github.com/cholnh/spring-best-practice-todo/blob/master/contents/guide-directory.md#아키텍처-및-directory-스타일-가이드)
- [Domain Model 및 범위 가이드](https://github.com/cholnh/spring-best-practice-todo/blob/master/contents/guide-domain.md#domain-model-및-범위-가이드)
- [Service 스타일 가이드](https://github.com/cholnh/spring-best-practice-todo/blob/master/contents/guide-service.md#service-스타일-가이드)
- [DAO 스타일 가이드](https://github.com/cholnh/spring-best-practice-todo/blob/master/contents/guide-dao.md#dao-스타일-가이드)
- [Test 스타일 가이드](https://github.com/cholnh/spring-best-practice-todo/blob/master/contents/guide-test.md#test-스타일-가이드)
- [Exception 스타일 가이드](https://github.com/cholnh/spring-best-practice-todo/blob/master/contents/guide-exception.md#exception-스타일-가이드)

<br/>

### 디렉토리 구조

- Domain 디렉토리  
    ```
    domain
        ├─common
        │  ├─cmap
        │  ├─fcm
        │  ├─file
        │  ├─kakao
        │  └─oauth
        │      ├─configuration
        │      ├─interceptor
        │      └─service
        ├─cs
        │  ├─faq
        │  │  ├─category
        │  │  ├─faq
        │  │  └─mapper
        │  ├─notice
        │  │  ├─mapper
        │  │  └─notice
        │  └─policy
        ├─delivery
        │  ├─deliverysite
        │  ├─detailsite
        │  └─region
        ├─marketing
        │  ├─advertisement
        │  │  ├─advertisement
        │  │  └─mapper
        │  ├─coupon
        │  │  ├─coupon
        │  │  └─mapper
        │  ├─event
        │  │  ├─event
        │  │  └─mapper
        │  ├─point
        │  │  ├─log
        │  │  └─rank
        │  └─promotion
        │      ├─mapper
        │      └─promotion
        ├─order
        │  ├─item
        │  │  ├─item
        │  │  └─sub
        │  ├─log
        │  ├─order
        │  ├─ordertime
        │  │  ├─mapper
        │  │  └─ordertime
        │  └─payment
        │      └─vbank
        ├─product
        │  ├─category
        │  ├─image
        │  ├─like
        │  ├─product
        │  ├─reply
        │  │  ├─like
        │  │  └─reply
        │  └─sub
        │      ├─category
        │      ├─image
        │      ├─mapper
        │      └─sub
        ├─staff
        ├─store
        │  ├─category
        │  ├─image
        │  ├─like
        │  ├─mapper
        │  ├─owner
        │  ├─review
        │  │  ├─image
        │  │  ├─like
        │  │  ├─reply
        │  │  │  ├─like
        │  │  │  └─reply
        │  │  └─review
        │  ├─store
        │  └─story
        │      ├─image
        │      └─story
        └─user
    ```

<br/>

- Global 디렉토리  
    ```
    global
        ├─annotation
        ├─configuration
        │  ├─database
        │  │  └─jpa
        │  ├─health
        │  ├─http
        │  ├─mapper
        │  └─rabbitmq
        ├─error
        │  └─exception
        ├─filter
        ├─util
        │  ├─bizm
        │  │  └─template
        │  ├─bootpay
        │  │  └─model
        │  │      ├─request
        │  │      └─response
        │  │          ├─callback
        │  │          ├─cancel
        │  │          └─verify
        │  ├─choseong
        │  ├─formatter
        │  ├─queryrunner
        │  ├─reflection
        │  ├─security
        │  ├─sqlinjection
        │  ├─time
        │  └─validation
        │      └─annotation
        └─_base
    ```

<br/>
