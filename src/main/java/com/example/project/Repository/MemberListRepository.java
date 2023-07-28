package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Group;
import com.example.project.Model.MemberList;
import com.example.project.Model.User;

@Repository
public interface MemberListRepository extends JpaRepository<MemberList,Integer> {
    //list of members from one group
    /*
     * @Query("SELECT m from MemberList m where m.groupId=?1")
     * List<MemberList> findGroupMembers(int groupId);
     * 
     * @Query("SELECT m from MemberList m where m.userId=?1 and m.groupId=?2")
     * MemberList findUser(User userId, Group groupId);
     */
    
    List<MemberList> findByGroupId(Group groupId);
    MemberList findByMemberUserIdAndGroupId(User user, Group group);
}
