package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.Group;
import com.example.project.Model.MemberList;
import com.example.project.Model.User;
import com.example.project.Repository.GroupRepository;
import com.example.project.Repository.MemberListRepository;

@Service
public class GroupService {

    private final GroupRepository GroupRepository;
    private final MemberListRepository memberListRepository;
    
    private final ActivityLogService activityLogService;

    @Autowired
    public GroupService(GroupRepository GroupRepository, MemberListRepository memberListRepository, ActivityLogService activityLogService) {
        this.GroupRepository = GroupRepository;
        this.memberListRepository = memberListRepository;
        this.activityLogService = activityLogService;
    }

    public List<Group> getUserGroup(User userId) {
        return GroupRepository.findUserGroups(userId);
    }
    
    //find specific group
    public Group findGroupByID(int groupId) {
        Group group = GroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException("Group with ID " + groupId + " does not exist"));
        return group;
    }

    public void addNewGroup(Group Group) {
        GroupRepository.save(Group);

        // add admin user to memberlist
        MemberList memberList = new MemberList();

        Group group2 = new Group();
        group2.setGroupId(Group.getGroupId());

        memberList.setGroupId(group2);
        memberList.setMemberUserId(Group.getGroupAdminUser());
        memberListRepository.save(memberList);
        
        //add activity log
        ActivityLog log= new ActivityLog();
        log.setUserId(Group.getGroupAdminUser());
        log.setMessage("You created a new group '"+Group.getGroupName()+"'");
        log.setCreatedAt(Group.getCreatedAt());
        activityLogService.addActivityLog(log);
    }

    public void deleteGroup(int GroupId) {
        // Retrieve the Group entity from the GroupRepository
        Group group = GroupRepository.findById(GroupId)
                .orElseThrow(() -> new IllegalStateException("group with id " + GroupId + " does not exist"));

        // On deletion of group, remove all its members
        List<MemberList> membersList = memberListRepository.findByGroupId(group);
        for (MemberList member : membersList) {
            memberListRepository.delete(member);
        }

        GroupRepository.delete(group);
    }

    @Transactional
    public void updateGroup(int GroupId, String GroupName) {
        Group Group = GroupRepository.findById(GroupId)
                .orElseThrow(() -> new IllegalStateException("group with id " + GroupId + " does not exist"));

        if (GroupName != null && GroupName.length() > 0 && !Objects.equals(Group.getGroupName(), GroupName)) {
            Group.setGroupName(GroupName);
        }
    }
}
