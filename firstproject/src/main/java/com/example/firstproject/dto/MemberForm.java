package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {
    private String email;
    private String password;

//    public MemberForm(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "MemberForm{" +
//                "email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }

    public Member toEntity() {
        return new Member(null, email, password);
    }
}
