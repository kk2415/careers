# Careers - 개발자 채용공고 통합 플랫폼

## 📋 프로젝트 개요

다양한 개발자 채용공고 사이트에서 공고 데이터를 크롤링하여 한 눈에 볼 수 있게 제공하는 통합 플랫폼입니다. 주요 IT 기업들의 채용공고를 수집하고, 사용자에게 검색 및 필터링 기능을 제공하며, 신규 공고 알림 서비스를 제공합니다.

## 🏗️ 아키텍처

### 백엔드 (Spring Boot)
- **job**: 메인 API 서버 (채용공고 조회, 검색, 관리)
- **crawler**: 채용공고 크롤링 서비스
- **notification**: Firebase 기반 푸시 알림 서비스
- **common**: 공통 모듈 (예외 처리, 설정 등)

### 프론트엔드 (Vite + TypeScript)
- **careers-page**: 사용자 인터페이스 (Vite + TypeScript + Axios)

## 🛠️ 기술 스택

### 백엔드
- **Java 17** (OpenJDK)
- **Spring Boot 2.x**
- **Spring Data JPA** + **QueryDSL**
- **Spring Security** + **JWT**
- **MySQL 8.0.26**
- **Docker** + **Docker Compose**
- **Firebase Admin SDK** (푸시 알림)

### 프론트엔드
- **Vite** (빌드 도구)
- **TypeScript**
- **Axios** (HTTP 클라이언트)

### 인프라
- **Docker** + **Docker Compose**
- **Nginx** (리버스 프록시)
- **MySQL** (데이터베이스)

## 🚀 주요 기능

### 1. 채용공고 크롤링
다음 기업들의 채용공고를 자동으로 수집합니다:
- 네이버, 카카오, 라인
- 쿠팡, 우아한형제, 토스
- 당근마켓, 오늘의 집, 야놀자
- SK, 쏘카 등

### 2. 채용공고 검색 및 필터링
- 회사별, 직무별, 지역별 검색
- 정렬 기능 (최신순, 마감일순 등)
- 페이지네이션 지원

### 3. 푸시 알림 서비스
- Firebase Cloud Messaging (FCM) 기반
- 신규 채용공고 알림
- 토픽 구독 기능

### 4. REST API
- Swagger UI를 통한 API 문서화
- JWT 기반 인증
- CORS 지원

## 📁 프로젝트 구조

```
careers/
├── job/                    # 메인 API 서버
│   ├── src/main/java/
│   │   └── com/careers/job/
│   │       ├── presentation/    # 컨트롤러, DTO
│   │       ├── domain/          # 도메인 모델, 서비스
│   │       ├── infrastructure/  # JPA, 외부 API
│   │       └── config/          # 설정 클래스
│   └── src/main/resources/
│       ├── application.yml
│       └── schema.sql
├── crawler/               # 크롤링 서비스
│   └── src/main/java/
│       └── com/careers/crawler/
│           ├── crawler/         # 크롤러 구현체
│           ├── domain/          # 도메인 모델
│           └── scheduler/       # 스케줄러
├── notification/          # 알림 서비스
│   └── src/main/java/
│       └── com/careers/notification/
│           ├── domain/          # 도메인 모델
│           ├── config/          # Firebase 설정
│           └── infrastructure/  # FCM 구현
├── common/                # 공통 모듈
│   └── src/main/java/
│       └── com/careers/common/
│           ├── config/          # 공통 설정
│           └── exception/       # 예외 처리
└── careers-page/          # 프론트엔드
    ├── src/
    │   ├── api/           # API 클라이언트
    │   ├── job/           # 채용공고 관련 로직
    │   └── main.ts        # 진입점
    └── public/            # 정적 파일
```

## 🚀 실행 방법

### 1. 환경 설정

#### 데이터베이스 설정
```yaml
# application.yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/{database}?serverTimezone=Asia/Seoul
    username: {username}
    password: {password}
```

#### 스키마 생성
```sql
-- resources/schema.sql 파일의 DDL 실행
```

### 2. Docker Compose로 실행

```bash
# 전체 서비스 실행
docker-compose up -d

# 개별 서비스 실행
docker-compose -f job/docker-compose.yml up -d
```

### 3. 로컬 개발 환경

#### 백엔드 실행
```bash
# job 모듈 빌드 및 실행
./gradlew :job:clean build
java -jar ./job/build/libs/job-1.0.0.jar --jasypt.encryptor.password={encryption_key}
```

#### 프론트엔드 실행
```bash
cd careers-page
npm install
npm run dev
```

## 🔧 API 문서

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Base URL**: `http://localhost:8080/api/v1`

### 주요 엔드포인트
- `GET /api/v1/jobs` - 채용공고 목록 조회
- `GET /api/v1/jobs/search` - 채용공고 검색
- `POST /api/v1/jobs/crawling` - 크롤링 실행 (관리자)
- `POST /api/v1/notifications/subscribe` - 알림 구독

## 🔐 보안

- **JWT 토큰** 기반 인증
- **Spring Security** 적용
- **CORS** 설정
- **관리자 권한** 필요 작업 분리

## 📊 모니터링

- **로깅**: Logback 설정
- **프로파일**: dev, prod 환경 분리
- **헬스체크**: Spring Boot Actuator
