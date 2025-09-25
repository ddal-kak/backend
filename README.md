## 1. 프로젝트 소개

Site : https://www.ddalkak.shop

사용자가 매일 로그인 시 지급되는 응모권으로 경품 이벤트에 참여할 수 있는 서비스입니다. 
분산 시스템에서의 필연적인 문제를 경험하기 위해 진행한 프로젝트로, 5개의 마이크로 서비스를 운영하며 안정성과 신뢰성을 확보한 시스템 구축을 목표로했습니다.  

![ddalkak (2).png](attachment:6fb74320-6869-4609-8f43-fc8fe1815dbc:ddalkak_(2).png)

## 2. 기술 스택

`Java17`  `Spring3.XX`  `MySQL`  `Kafka`  `Redis`  `AWS`

## 3. 주요 구현 결과

### [공통]

- Transactional Outbox 패턴을 통한 DB 트랜잭션과 이벤트 발행의 원자성 보장
- DB 최적화를 위한 Unique ID Generator 구현
- AOP를 통한 EventID 기반의 Idempotent Consumer 구현
- 분산 트랜잭션 간 데이터 정합성 보장
- Kafka Retry / Dead Letter Topic 운영을 통한 Head-of-Line Blocking 이슈 해소

### [Draw]

- 로그인 이벤트 수신 후 데일리 로그인 리워드 응모권 지급
- 회원가입 이벤트 수신 후 유저 응모권 초기화
- 응모 로직 구현
    - Prize Service로부터 당첨 관련 정보 fetch
    - Random Generator 로부터 난수를 생성, 해당 경품의 winning number와 일치하면 당첨
    - 당첨 이벤트 발행 → Prize Service 측에서 재고 감소 처리
        - 성공 시 최종 당첨
        - 재고 부족 시 실패
        - 예외 발생 시 응모권 롤백

### [Prize]

- 보상 트랜잭션을 위한 DLT 핸들링
    - 최종 응모 당첨 이벤트 핸들링 실패시, 응모권 롤백을 위한 보상 트랜잭션 이벤트 발행
- 무한 스크롤링 페이징 처리
    - 성능 향상을 위해 cursor 방식을 이용

### [Member]

- Spring Security + JWT를 통한 로그인 프로세스 구현
    - XSS, CSRF 공격을 고려한 쿠키 세팅으로 토큰 반환

### [Authorizer]

- Target API 서버 앞 단에 실행되어 미인증/인가 트래픽을 사전 차단
- 유저 정보를 파싱해 커스텀 헤더로 심어 Target API 서버로 전달
    - 다른 마이크로서비스의 불필요한 JWT 종속성 제거 효과
- Adapter 패턴 적용으로 Target Validator 조회 간 반복되는 조건 분기문을 해소
- 커스텀 AOP를 통한 인증 실패 예외 핸들링
    - 케이스 별 에러 메세지 반환

### [PresignedURL Generator]

- 서버 측 I/O를 줄이기 위해 클라이언트 측의 이미지 업로드 구현
- 상품 이미지를 AWS CloudFront에 저장하기 위한 Presigned Upload URL을 제공