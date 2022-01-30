# 코드로 배우는 스프링 부트 웹 프로젝트

이 레포지토리는 `코드로 배우는 스프링 부트 웹 프로젝트` 책을 통하여 공부한 내용을 기록을 남기기 위한 레포지토리입니다.

코드는 책을 보며 예제를 따라한 것이며, 혹여 나중에 필요한 코드가 있다면 이 레포지토리를 참고하기 위해 만들었습니다.

사용한 기술은 다음과 같습니다.

- Spring Boot 2.5.x
- Spring Data JPA
  - Querydsl
- MariaDB
- Spring Security

## 검색 기능

검색 기능을 추가할 때 Querydsl을 이용합니다. Querydsl 설정은 `build.gradle`을 참고해주세요.

- '제목/내용/작성자'와 같이 단 하나의 항목으로 검색하는 경우
- '제목+내용'/'내용+작성자'/'제목+작성자'와 같이 2개의 항목으로 검색하는 경우
- '제목+내용+작성자'와 같이 3개의 항목으로 검색하는 경우

## 목록처리

모든 목록을 처리하는 기능에는 페이지 번호나 한 페이지당 몇 개나 출력될 것인지와 같은 공통적인 부분이 많기 때문에 이를 클래스를 이용하면 좀 더 코드가 깔끔해집니다.

- 화면에서 필요한 목록 데이터에 대한 DTO 생성
- DTO를 Pageable 타입으로 전환
- Page<Entity>를 화면에서 사용하기 쉬운 DTO의 리스트 등으로 변환
- 화면에 필요한 페이지 번호 처리

## 페이징 처리

- 화면에서 시작 페이지 번호(start)
- 화면에서 끝 페이지 번호(end)
- 이전/다음 이동 링크 여부(prev, next)
- 현재 페이지 번호(page)