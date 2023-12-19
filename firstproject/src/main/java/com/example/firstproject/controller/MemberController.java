package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member/signup")
    public String newMember() {
        return "member/signup";
    }

    @PostMapping("/join")
    public String signup(MemberForm memberForm) {
//        System.out.println(memberForm.toString());
        log.info(memberForm.toString());
        Member entity = memberForm.toEntity();
//        System.out.println(entity.toString());
        log.info(entity.toString());
        Member save = memberRepository.save(entity);
//        System.out.println(save.toString());
        log.info(save.toString());
        return "redirect:/members";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Optional<Member> member = memberRepository.findById(id);
        model.addAttribute("member", member.get());
        return "member/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        List<Member> memberList = (List<Member>)memberRepository.findAll();
        model.addAttribute("memberList", memberList);
        return "member/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "member/edit";
    }

    @PostMapping("/member/update")
    public String update(MemberForm memberForm){
        log.info(memberForm.toString());
        Member member = memberForm.toEntity();

        Member member1 = memberRepository.findById(member.getId()).orElse(null);
        if (member1 != null){
            memberRepository.save(member);
        }
        return "redirect:/members/" + member1.getId();
    }
}
