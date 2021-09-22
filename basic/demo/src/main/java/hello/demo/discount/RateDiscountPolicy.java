package hello.demo.discount;

import hello.demo.annotation.MainDiscountPolicy;
import hello.demo.member.Grade;
import hello.demo.member.Member;
import org.springframework.stereotype.Component;

@Component
//@Primary // 같은 타입의 빈이 중복 등록될 경우 Primary 붙은 빈을 우선적으로 주입
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
         if(member.getGrade()== Grade.VIP){
             return price*discountPercent/100;
         } else {
             return 0;
         }
    }
}
