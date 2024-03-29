package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// 두 개를 합친 것 @Controller @ResponseBody
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    // 엔티티가 외부에 노출된 다.
    // 엔티티를 변경할 시에 api 스펙이 변경된다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m ->
                new MemberDto(m.getName())).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                                @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    // 생성자 자동으로 받는 어노테이션
    static class UpdateMemberResponse {
        private Long id;
        private String name;
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
