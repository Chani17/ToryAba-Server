package inu.thebite.toryaba.config;


import inu.thebite.toryaba.config.jwt.TokenProvider;
import inu.thebite.toryaba.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    /**
     * Spring Security 모든 기능 비활성화
     * 즉, 인증, 인가 서비스를 모든 곳에 적용하지는 않는다.
     * 일반적으로 정적 리소스(이미지, HTML 파일)에 설정한다. 즉, 정적 리소스만 spring security 사용을 비활성화
     * 현재는 사용하지 않을 것 같아 주석처리
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }


    /**
     * 특정 HTTP 요청에 대한 보안 구성
     * 해당 메서드에서는 인증/인가 및 로그인, 로그아웃 관련 설정을 할 수 있다.
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        return http

                // session 사용 x
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증, 인가 설정
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new MvcRequestMatcher(introspector, "/members/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/error")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/categories")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/notices/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/centers/**")).hasRole("DIRECTOR")
                        .anyRequest().authenticated()
                )
                // 폼 기반 로그인 설정
                // defaultSuccessUrl 설정 추가 여부
//                .formLogin(form -> form
//                        .loginPage("/members/login").permitAll()
//                        // 로그인 성공 시 보여줘야할 화면은?
//                        /*.defaultSuccessUrl("/")*/
//                )
                // 로그아웃 설정
//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/members/login")
//                )
                // csrf 비활성화(나중에는 활성화 해야함)
                .csrf((csrf) -> csrf.disable())
                .httpBasic((basic) -> basic.disable())
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    /**
     * 인증 관리자 관련 설정
     * 사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법(LDAP, JDGC 기반 인증 등)을 설정할 때 사용
     */
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception {
//       return http
//               .getSharedObject(AuthenticationManagerBuilder.class)
//               .userDetailsService(userService)
//               .passwordEncoder(bCryptPasswordEncoder)
//               // 다른 방법 찾아보기
//               .and()
//               .build();
//    }

    /**
     * AuthenticationManager는 Spring Security 인증 담당
     * 사용자 인증 시 UserDetailsService와 PasswordEncoder를 사용
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
