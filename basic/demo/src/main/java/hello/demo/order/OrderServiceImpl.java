package hello.demo.order;

import hello.demo.annotation.MainDiscountPolicy;
import hello.demo.discount.DiscountPolicy;
import hello.demo.member.Member;
import hello.demo.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // 롬복: final 붙은 필드만 뽑아다가 생성자 자동으로 만듦
public class OrderServiceImpl implements OrderService {

    // final이면 기본으로 할당하든, 생성자로 할당하든 꼭꼭 할당 해줘야 한다.
    // @Autowired // 필드 주입
    private final MemberRepository memberRepository;
    // @Autowired
    private final DiscountPolicy discountPolicy;


     // 생성자가 한 개면 Autowired 생략 가능
    // 빈이 중복해서 등록되있으면 필드 명이나 파라미터 명으로 빈 이름 매칭 가능
    // 빈이 중복될 경우 보통 주로 쓰는걸 Primary로 하고 그 외의 것을 Qualifier설정 한다 but 우선순위는 Qualifier가 더 높음
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    @Autowired // setter 주입
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired // setter 주입
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }


//    @Autowired // 일반 메서드 주입
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
