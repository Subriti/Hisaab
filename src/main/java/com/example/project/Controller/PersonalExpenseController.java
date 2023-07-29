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

import com.example.project.Model.PersonalExpense;
import com.example.project.Model.User;
import com.example.project.Service.PersonalExpenseService;

@RestController
@RequestMapping(path = "api/personalexpense")
public class PersonalExpenseController {

    private final PersonalExpenseService PersonalExpenseService;

    @Autowired
    public PersonalExpenseController(PersonalExpenseService PersonalExpenseService) {
        this.PersonalExpenseService = PersonalExpenseService;
    }

    @GetMapping("/showUserExpenses/{userId}")
    public List<PersonalExpense> getUserPersonalExpense(@PathVariable("userId") User userId) {
        return PersonalExpenseService.getUserPersonalExpense(userId);
    }

    @GetMapping("/showExpensesByCategory/{userId}")
    public List<PersonalExpense> getUserExpenseByCategory(@PathVariable("userId") int userId, int categoryId) {
        return PersonalExpenseService.getUserPersonalExpenseByCategory(userId, categoryId);
    }
    
    @GetMapping("/showTotalExpensesByCategory/{userId}")
    public Double getTotalExpenseByCategory(@PathVariable("userId") int userId, int categoryId) {
        return PersonalExpenseService.getTotalExpensesByCategory(userId, categoryId);
    }

    @GetMapping("/showMonthlyExpenses/{userId}")
    public Double getMonthlyExpense(@PathVariable("userId") int userId, String start, String end) {
        return PersonalExpenseService.getTotalExpensesByMonth(userId, start, end);
    }

    @GetMapping("/showTotalExpenses/{userId}")
    public Double getTotalExpenses(@PathVariable("userId") User userId) {
        return PersonalExpenseService.getTotalExpensesAllTime(userId);
    }

    @GetMapping(path = "/findPersonalExpense/{personalexpenseId}")
    public PersonalExpense findPersonalExpenseByID(@PathVariable int personalexpenseId) {
        return PersonalExpenseService.findPersonalExpenseByID(personalexpenseId);
    }

    @PostMapping("/addPersonalExpense")
    public void addNewPersonalExpense(@RequestBody PersonalExpense PersonalExpense) {
        PersonalExpenseService.addNewPersonalExpense(PersonalExpense);
    }

    @DeleteMapping(path = "/deletePersonalExpense/{PersonalExpenseId}")
    public void deletePersonalExpense(@PathVariable("PersonalExpenseId") int PersonalExpenseId) {
        PersonalExpenseService.deletePersonalExpense(PersonalExpenseId);
    }

    @PutMapping(path = "/updatePersonalExpense")
    public void updatePersonalExpense(@RequestBody PersonalExpense personalExpense) {
        PersonalExpenseService.updatePersonalExpense(personalExpense);
    }
}
