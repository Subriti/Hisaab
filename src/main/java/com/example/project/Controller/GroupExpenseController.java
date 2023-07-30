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
import com.example.project.Model.GroupExpense;
import com.example.project.Model.User;
import com.example.project.Service.GroupExpenseService;

@RestController
@RequestMapping(path = "api/groupexpense")
public class GroupExpenseController {

    private final GroupExpenseService GroupExpenseService;

    @Autowired
    public GroupExpenseController(GroupExpenseService GroupExpenseService) {
        this.GroupExpenseService = GroupExpenseService;
    }

    @GetMapping("/showGroupExpenses/{groupId}")
    public List<GroupExpense> getGroupGroupExpense(@PathVariable("groupId") Group groupId) {
        return GroupExpenseService.getGroupExpense(groupId);
    }

    @GetMapping("/showMonthlyGroupExpense/{groupId}")
    public Double getMonthlyExpense(@PathVariable("groupId") int groupId, String startDate, String endDate) {
        return GroupExpenseService.getTotalExpensesByMonth(groupId, startDate, endDate);
    }

    @GetMapping("/showTotalGroupExpense/{groupId}")
    public Double getTotalExpenses(@PathVariable("groupId") Group groupId) {
        return GroupExpenseService.getTotalExpensesAllTime(groupId);
    }

    @GetMapping(path = "/findGroupExpense/{groupexpenseId}")
    public GroupExpense findGroupExpenseByID(@PathVariable int groupexpenseId) {
        return GroupExpenseService.findGroupExpenseByID(groupexpenseId);
    }

    @PostMapping(path = "/addGroupExpense")
    public void addGroupExpense(@RequestBody GroupExpense groupExpense) {
        GroupExpenseService.addGroupExpense(groupExpense);
    }

    @DeleteMapping(path = "/deleteGroupExpense/{GroupExpenseId}")
    public void deleteGroupExpense(@PathVariable("GroupExpenseId") int GroupExpenseId) {
        GroupExpenseService.deleteGroupExpense(GroupExpenseId);
    }

    @PutMapping(path = "/updateGroupExpense")
    public void updateGroupExpense(@RequestBody GroupExpense groupExpense) {
        GroupExpenseService.updateGroupExpense(groupExpense);
    }

    @PutMapping(path = "/addSplitParticipants/{GroupExpenseId}")
    public GroupExpense addParticipants(@PathVariable("GroupExpenseId") int GroupExpenseId,
            @RequestBody List<User> participants) {
        return GroupExpenseService.addParticipants(GroupExpenseId, participants);
    }

    @PutMapping(path = "/splitExpense/{GroupExpenseId}")
    public GroupExpense splitExpense(@PathVariable("GroupExpenseId") int GroupExpenseId) {
        return GroupExpenseService.splitExpense(GroupExpenseId);
    }

    @PutMapping(path = "/balance/{GroupExpenseId}")
    public Double getBalance(@PathVariable("GroupExpenseId") int GroupExpenseId) {
        return GroupExpenseService.getsBackBalance(GroupExpenseId);
    }
    
    @PostMapping(path = "/paymentReminder/{GroupExpenseId}")
    public void remindPayment(@PathVariable("GroupExpenseId") int GroupExpenseId, User userId) {
        GroupExpenseService.remindForPayment(GroupExpenseId, userId);
    }
    
    @PostMapping(path = "/settlePayment/{GroupExpenseId}")
    public void settlePayment(@PathVariable("GroupExpenseId") int GroupExpenseId, User userId) {
        GroupExpenseService.settlePayment(GroupExpenseId, userId);
    }
}
