package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Group;
import com.example.project.Model.User;
import com.example.project.Service.GroupService;

@RestController
@RequestMapping(path = "api/group")
public class GroupController {

    private final GroupService GroupService;

    @Autowired
    public GroupController(GroupService GroupService) {
        this.GroupService = GroupService;
    }

    @GetMapping("/showUserGroups/{userId}")
    public List<Group> getUserGroup(@PathVariable("userId") User userId) {
        return GroupService.getUserGroup(userId);
    }
    
    @GetMapping(path = "/findGroup/{groupId}")
    public Group findGroupByID(@PathVariable int groupId) {
        return GroupService.findGroupByID(groupId);
    }

    @PostMapping("/addGroup")
    public void addNewGroup(@RequestBody Group Group) {
        GroupService.addNewGroup(Group);
    }

    @DeleteMapping(path = "/deleteGroup/{GroupId}")
    public void deleteGroup(@PathVariable("GroupId") int GroupId) {
        GroupService.deleteGroup(GroupId);
    }

    @PutMapping(path = "/updateGroup/{GroupId}")
    public void updateGroup(@PathVariable("GroupId") int GroupId, String GroupName) {
        GroupService.updateGroup(GroupId, GroupName);
    }
}
