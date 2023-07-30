package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Group;
import com.example.project.Model.GroupExpense;

@Repository
public interface GroupExpenseRepository extends JpaRepository<GroupExpense, Integer> {
    List<GroupExpense> findByGroupId(Group groupId);
    
    @Query(value = "select sum(amount) from group_expenses_amount_owed where group_expenses_id=?1", nativeQuery = true)
     Double getsBackBalance(int groupExpenseId);
}