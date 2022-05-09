package com.cos.jwt.config;

import com.cos.jwt.filter.JwtAuthenticationFilter;
import com.cos.jwt.filter.JwtAuthorizationFilter;
import com.cos.jwt.filter.MyFilter1;
import com.cos.jwt.filter.MyFilter3;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        // 세션 사용안함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
                .addFilter(corsFilter)
                // formLogin 사용 안함
                .formLogin().disable()
                // 매 요청마다 id, pwd를 보내는 방식으로 인증하는 httpBasic을 사용하지 않겠다.
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager를 param으로 줘야됨
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository)) // AuthenticationManager를 param으로 줘야됨
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }
}
