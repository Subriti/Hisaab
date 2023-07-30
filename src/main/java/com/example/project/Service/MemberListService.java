package com.example.project.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.Group;
import com.example.project.Model.MemberList;
import com.example.project.Model.Notification;
import com.example.project.Model.User;
import com.example.project.Model.UserSession;
import com.example.project.Repository.MemberListRepository;

import net.minidev.json.JSONObject;

@Service
public class MemberListService {

    private final MemberListRepository memberListRepository;
    private final NotificationService notificationService;
    private final GroupService groupService;
    private final UserService userService;
    
    private final UserSession userSession;
    private final ActivityLogService activityLogService;

    @Autowired
    public MemberListService(MemberListRepository memberListRepository, NotificationService notificationService, UserSession userSession, ActivityLogService activityLogService, GroupService groupService, UserService userService) {
        this.memberListRepository = memberListRepository;
        this.notificationService = notificationService;
        this.groupService = groupService;
        this.userService = userService;
        this.userSession = userSession;
        this.activityLogService = activityLogService;
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
            
            User user= userService.findUserByID(memberList.getMemberUserId().getUserId());            
            Group group= groupService.findGroupByID(memberList.getGroupId().getGroupId());
            
            // add notification for the added member
            Notification notification = new Notification();
            notification.setTitle("New Group Added");
            notification.setMessage("You were added to the group '" + group.getGroupName() + "'");
            notification.setRecieverId(memberList.getMemberUserId());
            notificationService.addNotification(notification);
            
          //add activity log
            ActivityLog log= new ActivityLog();
            System.out.println("Current user: "+userSession.getUserDetails().getName());
            
            log.setUserId(userSession.getUserDetails());
            log.setMessage("You added '"+ user.getName()+"' to the group '"+ group.getGroupName() +"'");
            log.setCreatedAt(new Date(System.currentTimeMillis()));
            activityLogService.addActivityLog(log);
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
