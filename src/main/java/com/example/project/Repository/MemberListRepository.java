package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Group;
import com.example.project.Model.MemberList;
import com.example.project.Model.User;

@Repository
public interface MemberListRepository extends JpaRepository<MemberList,Integer> {
    //list of members from one group    
    List<MemberList> findByGroupId(Group groupId);
    MemberList findByMemberUserIdAndGroupId(User user, Group group);
}
