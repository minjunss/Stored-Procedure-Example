package com.StoredProcedure.StoredProcedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/{id}")
    public Map<String, Object> getMember(@PathVariable int id) {
        return memberService.getMemberDetails(id);
    }

    @PostMapping("/member/add")
    public void addMember(@RequestBody SaveMemberDto saveMemberDto) {
        memberService.saveMember(saveMemberDto);
    }
}
