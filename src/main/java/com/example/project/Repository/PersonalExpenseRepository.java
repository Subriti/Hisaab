package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.PersonalExpense;
import com.example.project.Model.User;

@Repository
public interface PersonalExpenseRepository extends JpaRepository<PersonalExpense, Integer> {
    List<PersonalExpense> findByUserId(User userId);
}