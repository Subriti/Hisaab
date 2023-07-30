package com.example.project.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.AmountOwedByUser;
import com.example.project.Model.Group;
import com.example.project.Model.GroupExpense;
import com.example.project.Model.Notification;
import com.example.project.Model.User;
import com.example.project.Model.UserSession;
import com.example.project.Repository.GroupExpenseRepository;

@Service
public class GroupExpenseService {

    private final GroupExpenseRepository GroupExpenseRepository;
    private final UserSession userSession;

    private final ActivityLogService activityLogService;
    private final GroupService groupService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public GroupExpenseService(GroupExpenseRepository GroupExpenseRepository, ActivityLogService activityLogService,
            UserSession userSession, GroupService groupService, NotificationService notificationService,
            UserService userService) {
        this.GroupExpenseRepository = GroupExpenseRepository;
        this.activityLogService = activityLogService;
        this.userSession = userSession;
        this.groupService = groupService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public List<GroupExpense> getGroupExpense(Group groupId) {
        return GroupExpenseRepository.findByGroupId(groupId);
    }

    private JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Double getAll(String queryString) {
        System.out.println("\n" + queryString);

        return template.query(queryString, new ResultSetExtractor<Double>() {

            @Override
            public Double extractData(ResultSet rs) throws SQLException, DataAccessException {

                Double amount = 0.0;

                while (rs.next()) {
                    amount = (rs.getDouble(1));
                    System.out.println("\nAmount is " + amount);
                }
                return amount;
            }
        });
    }

    public Double getTotalExpensesByMonth(int groupId, String start, String end) { // date in format (2023-06-28)
        String queryString = "select sum(amount) from group_expenses where group_id=" + groupId
                + " and created_at between '" + start + "' and '" + end + "'";
        return getAll(queryString);
    }

    public Double getTotalExpensesAllTime(Group groupId) {
        return GroupExpenseRepository
                .findByGroupId(groupId)
                .stream()
                .mapToDouble(GroupExpense::getAmount)
                .sum();
    }

    // find specific groupExpense
    public GroupExpense findGroupExpenseByID(int groupExpenseId) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(groupExpenseId)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with ID " + groupExpenseId + " does not exist"));
        return groupExpense;
    }

    public GroupExpense addGroupExpense(GroupExpense groupExpense) {
        if (groupExpense == null) {
            throw new IllegalArgumentException("GroupExpense object cannot be null.");
        }

        Group group = groupService.findGroupByID(groupExpense.getGroupId().getGroupId());

        // add activity log
        ActivityLog log = new ActivityLog();
        log.setUserId(userSession.getUserDetails());
        log.setMessage("You added a new expense of '" + groupExpense.getAmount() + "' in group: '"
                + group.getGroupName() + "'");
        log.setCreatedAt(new Date(System.currentTimeMillis()));
        activityLogService.addActivityLog(log);

        // add notification for the members in the group
        Notification notification = new Notification();
        notification.setTitle("New Group Expense added");
        notification.setMessage(
                "Expense of '" + groupExpense.getAmount() + "' was added to group: " + group.getGroupName() + "'");
        notification.setGroupId(group);
        notificationService.addNotification(notification);

        return GroupExpenseRepository.save(groupExpense);
    }

    public void deleteGroupExpense(int GroupExpenseId) {
        // Retrieve the GroupExpense entity from the GroupExpenseRepository
        GroupExpense groupExpense = GroupExpenseRepository.findById(GroupExpenseId)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with id " + GroupExpenseId + " does not exist"));
        GroupExpenseRepository.delete(groupExpense);
    }

    @Transactional
    public void updateGroupExpense(GroupExpense GroupExpense) {
        GroupExpense expense = GroupExpenseRepository.findById(GroupExpense.getGroupExpenseId())
                .orElseThrow(() -> new IllegalStateException(
                        "GroupExpense with id " + GroupExpense.getGroupExpenseId() + " does not exist"));

        if (GroupExpense.getAmount() != 0 && !Objects.equals(GroupExpense.getAmount(), expense.getAmount())) {
            expense.setAmount(GroupExpense.getAmount());
        }

        if (GroupExpense.getDescription() != null && GroupExpense.getDescription().length() > 0
                && !Objects.equals(GroupExpense.getDescription(), expense.getDescription())) {
            expense.setDescription(GroupExpense.getDescription());
        }

        if (GroupExpense.getPaidBy() != null && !Objects.equals(GroupExpense.getPaidBy(), expense.getPaidBy())) {
            expense.setPaidBy(GroupExpense.getPaidBy());
        }
    }

    public GroupExpense addParticipants(int id, List<User> participants) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with ID " + id + " does not exist"));
        groupExpense.getParticipants().addAll(participants);
        return GroupExpenseRepository.save(groupExpense);
    }

    public GroupExpense splitExpense(int id) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with ID " + id + " does not exist"));
        int participantCount = groupExpense.getParticipants().size();
        double amountPerParticipant = groupExpense.getAmount() / participantCount;

        Map<User, AmountOwedByUser> amountOwedByParticipants = new HashMap<>();
        for (User participant : groupExpense.getParticipants()) {
            if (!participant.equals(groupExpense.getPaidBy())) {
                AmountOwedByUser amountOwedByUser = new AmountOwedByUser();
                amountOwedByUser.setAmount(amountPerParticipant);
                amountOwedByUser.setisPaid(false);
                amountOwedByParticipants.put(participant, amountOwedByUser);
            }
        }

        groupExpense.setAmountOwedByParticipants(amountOwedByParticipants);
        return GroupExpenseRepository.save(groupExpense);
    }

    public void remindForPayment(int expenseId, User user) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(expenseId)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with ID " + expenseId + " does not exist"));

        Map<User, AmountOwedByUser> amountOwedByParticipant = groupExpense.getAmountOwedByParticipants();

        AmountOwedByUser amountOwedByUser = null;

        for (User userKey : amountOwedByParticipant.keySet()) {
            amountOwedByUser = amountOwedByParticipant.get(userKey);
        }

        Group group = groupService.findGroupByID(groupExpense.getGroupId().getGroupId());

        // add notification for the payment reminder
        Notification notification = new Notification();
        notification.setTitle("Payment Reminder");
        notification.setMessage("Payment of '" + amountOwedByUser.getAmount() + "' needs to be settled in the group: "
                + group.getGroupName() + "' for the expense: '" + groupExpense.getDescription() + "'");
        notification.setRecieverId(user);
        notificationService.addNotification(notification);
    }

    public void settlePayment(int expenseId, User user) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(expenseId)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with ID " + expenseId + " does not exist"));

        Map<User, AmountOwedByUser> amountOwedByParticipant = groupExpense.getAmountOwedByParticipants();

        AmountOwedByUser amountOwedByUser = null;

        for (User userKey : amountOwedByParticipant.keySet()) {
            if (userKey.getUserId() == (user.getUserId())) {
                amountOwedByUser = amountOwedByParticipant.get(userKey);
                break; 
            }
        }

        if (amountOwedByUser != null) {
            // User is present in the map, change isPaid status to true
            amountOwedByUser.setisPaid(true);
            amountOwedByParticipant.put(user, amountOwedByUser);
            groupExpense.setAmountOwedByParticipants(amountOwedByParticipant);
            GroupExpenseRepository.save(groupExpense);

            Group group = groupService.findGroupByID(groupExpense.getGroupId().getGroupId());
            User sender = userService.findUserByID(user.getUserId());

            // add activity log
            ActivityLog log = new ActivityLog();
            log.setUserId(user);
            log.setMessage("You settled payment of '" + amountOwedByUser.getAmount() + "' in group: '"
                    + group.getGroupName() + "' for the expense: '" + groupExpense.getDescription() + "'");
            log.setCreatedAt(new Date(System.currentTimeMillis()));
            activityLogService.addActivityLog(log);

            // add notification for the paidBy member
            Notification notification = new Notification();
            notification.setTitle("Payment Settlement");
            notification.setMessage("Payment of '" + amountOwedByUser.getAmount() + "' was settled by '"
                    + sender.getName() + "' in the group: "
                    + group.getGroupName() + "' for the expense: '" + groupExpense.getDescription() + "'");
            notification.setRecieverId(groupExpense.getPaidBy());
            notificationService.addNotification(notification);

        } else {
            System.out.println("User " + user + " is not part of the amountOwedByParticipants.");
        }
    }

    public Double getsBackBalance(int id) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("GroupExpense with ID " + id + " does not exist"));
        return GroupExpenseRepository.getsBackBalance(groupExpense.getGroupExpenseId());
    }
}
