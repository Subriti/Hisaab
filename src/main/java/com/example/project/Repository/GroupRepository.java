package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Group;
import com.example.project.Model.User;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Query("select g from Group g join MemberList m on m.groupId= g.groupId where m.memberUserId=?1")
    List<Group> findUserGroups(User userId);
}
