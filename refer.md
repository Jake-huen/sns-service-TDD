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

## Spring 3에서는 SpringSecurity 6 사용

- `authorizeHttpRequests()` 존재하지 않음
- `csrf()` 존재하지 않음
```java
@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/*/users/join", "/api/*/users/login").permitAll()
                .requestMatchers("/api/**").authenticated()
        )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // TODO : Security EntryPoint
//                .exceptionHandling((exception) -> exception
//                        .authenticationEntryPoint());
return http.build();
}
}
```

## @WithAnonymousUser, @WithMockUser 어노테이션

- 스프링 시큐리티에서 제공하는 어노테이션 중 하나
- 테스트 메서드에서 익명 사용자로 테스트를 수행하는데 사용

## (columnDefinition = "TEXT") 

- Entity를 작성할 때, @Column의 속성값으로 (columnDefinition = "TEXT")를 사용하면 String으로 사용할 때의 길이보다 더 긴 내용을 사용할 수 있다.


## doThrow(new SnsApplicationException(ErrorCode.INVALID_PERMISSION)).when(postService).modify(title, body, any(), eq(1));

- 특정 오류를 발생시키고 싶을때 사용
- `any()`, `eq(1)` 사용
  - `eq(1)` 메서드는 Mockito의 매치어 중 하나로, 인자로 전달된 값을 비교할 때 사용함. 주로 메서드 호출 시 인자로 전달되는 값이 일치하는지 확인하기 위해 사용.
    -   eq(1)은 인자가 1과 일치하는지를 확인하기 위한 매치어