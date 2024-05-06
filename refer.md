# Autowired와 Mockbean의 차이

## @Autowired

- Spring의 의존성 주입을 위한 어노테이션
- @Autowired가 붙은 필드, 생성자, 메서드에 대해 Spring은 해당 타입의 빈을 찾아 자동으로 주입
- Spring의 ApplicationContext에서 관리되는 실제의 빈 인스턴스
- **실제 객체**를 사용해야 할 때 사용

## @MockBean

- SpringBootTest에서 제공하는 어노테이션
- 해당 타입의 빈을 모킹버전으로 교체
- 모킹된 객체는 실제 로직을 수행하지 않고, 원하는 대로 동작을 설정할 수 있다.
- (예 : 특정 메서드가 호출되면 어떤 값을 리턴하도록 설정)
- @MockBean은 주로 테스트 환경에서 특정 빈의 실제 동작을 원치 않고, 미리 정의된 동작을 수행하게 하기 위해 사용

@Autowired는 실제 서비스나 구성요소를 주입받아 실제 환경과 유사한 조건에서 테스트하고자 할 때 사용

@MockBean은 외부 시스템의 호출, 복잡한 로직 등을 모의로 처리하여, 테스트에만 초점을 맞출 수 있게 해준다.



# fixture

로그인 과정에서 password 비교하는 부분이 있다.
Mocking된 userEntity를 가지고 비교하는 로직을 구현해도 괜찮지만, fixture를 만들어서 fixture를 가지고
userEntity에 있는 패스워드랑 실제 입력받은 패스원드 비교하는게 더 쉽도록 코드 구현할 수 있다.

-> 가짜 테스트용 userEntity