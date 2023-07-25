package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 두 개를 합친 것 @Controller @ResponseBody
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    // @requestbody json 데이터를 member로 바꿔주는 어노테이션
    // @valid 검증 로직을 통합하는 어노테이션
    // 엔티티에서 속성이 수정될 경우 api 설계 자체가 변하기 때문에 Entity를 파라미터로 받는 것은 위험함 따라서 api를 위한 dto를 만드는게 좋음
    // api를 만들 때는 엔티티로 바인딩하면 안 됨, 또한 노출시켜서도 안 됨
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
