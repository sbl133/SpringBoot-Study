package com.cos.security1.cofing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(아래 SecurityConfig class)가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성, preAuthorize, postAutorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 해당 메서드의 리턴되는 메서드를 Ioc로 등록
   @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                // 로그인 한 사람만 들어올 수 있음
                .antMatchers("/user/**").authenticated()
                // 로그인 한 사람중 ROLE~ 권한을 가지 사람만 들어올 수 있음
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                // 설정하지 않은 주소는 접근 허용
                .anyRequest().permitAll()
                // 권한이 필요한 페이지를 권한 없이 접근할 경우 login페이지로 이동
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행 - login 페이지를 따로 안만들어도 됨
//                .usernameParameter("username2") // form에 username를 바꾸고 싶으면 여기서 parameter 이름을 매칭해줘야 PrincipalDetailsService가 알아먹음
                .defaultSuccessUrl("/");
    }
}
