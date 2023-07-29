package com.example.project.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.example.project.Model.Group;
import com.example.project.Model.GroupExpense;
import com.example.project.Model.User;
import com.example.project.Repository.GroupExpenseRepository;

@Service
public class GroupExpenseService {

    private final GroupExpenseRepository GroupExpenseRepository;

    @Autowired
    public GroupExpenseService(GroupExpenseRepository GroupExpenseRepository) {
        this.GroupExpenseRepository = GroupExpenseRepository;
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
    
    
    //existing code modifications
    public GroupExpense addParticipants(int id, List<User> participants) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(id).orElse(null);
        if (groupExpense != null) {
            groupExpense.getParticipants().addAll(participants);
            return GroupExpenseRepository.save(groupExpense);
        }
        return null;
    }

    
    public GroupExpense splitExpense(int id) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(id).orElse(null);
        if (groupExpense != null) {
            int participantCount = groupExpense.getParticipants().size();
            double amountPerParticipant = groupExpense.getAmount() / participantCount;

            Map<User, Double> amountOwedByParticipants = new HashMap<>();
            for (User participant : groupExpense.getParticipants()) {
                if (!participant.equals(groupExpense.getPaidBy())) {
                    amountOwedByParticipants.put(participant, amountPerParticipant);
                }
            }

            groupExpense.setAmountOwedByParticipants(amountOwedByParticipants);
            return GroupExpenseRepository.save(groupExpense);
        }
        return null;
    }
    
    
    public Double getsBackBalance(int id) {
        GroupExpense groupExpense = GroupExpenseRepository.findById(id).orElse(null);
        if (groupExpense != null) {
            return GroupExpenseRepository.getsBackBalance(id);
        }
        return null;
    }
}
