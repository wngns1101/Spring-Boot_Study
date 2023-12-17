package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member/signup")
    public String newMember() {
        return "member/signup";
    }

    @PostMapping("join")
    public String signup(MemberForm memberForm) {
        System.out.println(memberForm.toString());

        Member entity = memberForm.toEntity();
        System.out.println(entity.toString());

        Member save = memberRepository.save(entity);
        System.out.println(save.toString());

        return "";
    }
}
