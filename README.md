## infrun

교육 강의 플랫폼인 인프런을 클론하여 그동안 학습한 것을 적용하고 점진적으로 개선해나가는 토이 프로젝트입니다.

## 기술 및 환경

![stacks](https://github.com/f-lab-edu/infrun/assets/40778768/37f8fbbf-9c06-482a-bba3-40bb10195ff8)

**BACKEND**    
Spring Boot 3.1.0, Java 17, Spring Data JPA, Gradle

**DATABASE**    
H2, MySQL 8.0

**INFRA**    
Docker, Github Actions, NAVER Cloud Platform

## 프로젝트 구조

![architecture](https://github.com/f-lab-edu/infrun/assets/40778768/42cc2d5f-11b3-4718-ab7e-a433d344aa4f)

## 앞으로 도전할 것

- 캐싱을 활용한 조회 성능 개선
- SPOF 회피
    - DB 레플리케이션
    - 다중 서버 로드밸런싱
- 대용량 데이터 업로드 (영상)
- 멀티 모듈 구조로 마이그레이션
- 이벤트 기반 아키텍처 도입
