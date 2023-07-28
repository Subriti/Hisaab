package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.Group;
import com.example.project.Model.MemberList;
import com.example.project.Model.User;
import com.example.project.Repository.MemberListRepository;

import net.minidev.json.JSONObject;

@Service
public class MemberListService {

    private final MemberListRepository memberListRepository;

    @Autowired
    public MemberListService(MemberListRepository memberListRepository) {
        this.memberListRepository = memberListRepository;
    }

    // show members on group details
    public List<MemberList> getGroupMembers(Group groupId) {
        return memberListRepository.findByGroupId(groupId);
    }

    // adding member to group
    public JSONObject addMember(MemberList memberList) {
        JSONObject jsonBody = new JSONObject();

        MemberList members = memberListRepository.findByMemberUserIdAndGroupId(memberList.getMemberUserId(),
                memberList.getGroupId());
        if (members != null) {
            jsonBody.clear();
            jsonBody.put("Error Message", "User is already added to the group");
        } else {

            memberListRepository.save(memberList);
            jsonBody.clear();
            jsonBody.put("Success Message", "User is added to the group");
        }

        return jsonBody;
    }

    // delete member from the group
    public JSONObject deleteMember(User userId, Group groupId) {
        JSONObject jsonBody = new JSONObject();
        MemberList members = memberListRepository.findByMemberUserIdAndGroupId(userId, groupId);
        if (members == null) {
            jsonBody.clear();
            jsonBody.put("Error Message", "User is not present in the group");
        } else {
            // removing user from the group
            memberListRepository.deleteById(members.getMemberId());
            jsonBody.put("Success Message", "User removed from the group");
        }
        return jsonBody;
    }
}
