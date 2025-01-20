# Spring Security - JWT - Refresh Token

## 🔎API 명세서
### 회원가입

- 사용자 정보 저장 로직(Service/Repository) 작성
- 비밀번호 암호화(PasswordEncoder) 처리
- 필수 입력값 검증 로직
  `Request Body`
```json
{
    "username": "JIN HO",
    "password": "12341234",
    "nickname": "Mentos"
}
```
`Response Body`
```json
{
    "username": "JIN HO",
    "nickname": "Mentos",
    "authorities": [
	{
	    "authority": "ROLE_USER"
	}
    ]		
}
```

### 로그인
- 사용자 인증 처리(Service/Repository)
- 성공 시 Access Token 및 Refresh Token 발행
  `Request Body`
```json
{
    "username": "JIN HO",
    "password": "12341234"
}
```
`Response Body`
```json
{
    "accessToken": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL",
    "accessToken": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjDOJFOIEHWOJDLI"
}
```

### 토큰 재발급
- Refresh Token을 이용한 Access Token 재발급
```json
{
  "refreshToken": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL"
}
```

### ⚠️ 예외 처리
* 중복 회원 가입 방지(이메일/아이디 중복 검사)
* 입력값 유효성 검증 및 예외 처리 로직 추가

* 잘못된 인증 정보로 요청 시 예외 처리

## 🌐배포 서버
```angular2html
http://43.202.56.142:8080
```

## 🖥️프로젝트 실행 방법

#### 1. 프로젝트를 clone 받기
```shell
$git clone https://github.com/crawling-project-crowrong/it-cast.git
```
#### 2. Docker Compose로 개발 환경 구성
```shell
$docker-compose up -d
```
#### 3. 애플리케이션 실행
