package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Group;
import com.example.project.Model.MemberList;
import com.example.project.Service.MemberListService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/memberList")
public class MemberListController {

    private final MemberListService MemberListService;

    @Autowired
    public MemberListController(MemberListService MemberListService) {
        this.MemberListService= MemberListService;
    }

    @GetMapping("/showGroupMembers/{groupId}")
    public List<MemberList> getGroupMemberList(Group groupId) {
        return MemberListService.getGroupMembers(groupId);
    }
    
    @PostMapping("/addMember")
    public JSONObject addMember(@RequestBody MemberList memberList) {
        return MemberListService.addMember(memberList);
    }
    
    @PostMapping("/deleteMember")
    public JSONObject deleteMember(@RequestBody MemberList memberList) {
        return MemberListService.deleteMember(memberList.getMemberUserId(), memberList.getGroupId());
    }
}
