package hello.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // @Component 에너테이션이 붙은 클래스를 찾아서 자동으로 스프링 빈으로 등록
//        basePackages = "hello.demo.member", // 컴포넌트스캔 시작 위치를 지정해 준다.
//        basePackageClasses = AutoAppConfig.class, // 지정한 클래스의 패키지를 시작 위치로
//        시작위치를 지정하지 않으면 ComponentScan 붙은 클래스의 패키지를 시작 위치로
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)

)
public class AutoAppConfig {

        // 이름이 겹칠경우 스프링부트는 컴파일 에러 처리한다.
//        @Bean(name = "memoryMemberRepository")
//        MemberRepository memberRepository(){
//                return new MemoryMemberRepository();
//        }
}
