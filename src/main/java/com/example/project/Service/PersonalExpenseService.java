package com.example.project.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.Category;
import com.example.project.Model.PersonalExpense;
import com.example.project.Model.User;
import com.example.project.Repository.PersonalExpenseRepository;

@Service
public class PersonalExpenseService {

    private final PersonalExpenseRepository PersonalExpenseRepository;

    private final ActivityLogService activityLogService;
    private final CategoryService categoryService;

    @Autowired
    public PersonalExpenseService(PersonalExpenseRepository PersonalExpenseRepository, ActivityLogService activityLogService, CategoryService categoryService) {
        this.PersonalExpenseRepository = PersonalExpenseRepository;
        this.activityLogService = activityLogService;
        this.categoryService = categoryService;
    }

    public List<PersonalExpense> getUserPersonalExpense(User userId) {
        return PersonalExpenseRepository.findByUserId(userId);
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

    public Double getTotalExpensesByMonth(int userId, String start, String end) { // date in format (2023-06-28)
        String queryString = "select sum(amount) from personal_expenses where user_id=" + userId
                + " and created_at between '" + start + "' and '" + end + "'";
        return getAll(queryString);
    }

    public List<PersonalExpense> getUserPersonalExpenseByCategory(int userId, int categoryId) {
        String query= "select personal_expense_id, description, amount, category_id from personal_expenses where user_id=" + userId + "and category_id=" + categoryId;
        return template.query(query, new ResultSetExtractor<List<PersonalExpense>>() {

            @Override
            public List<PersonalExpense> extractData(ResultSet rs) throws SQLException, DataAccessException {

                PersonalExpense s = new PersonalExpense();
                Category category= new Category();
                List<PersonalExpense> list = new ArrayList<>();

                while (rs.next()) {
                    s = new PersonalExpense();

                    s.setPersonalExpenseId(rs.getInt(1));
                    s.setDescription(rs.getString(2));
                    s.setAmount(rs.getInt(3));
                    
                    category.setCategoryId(rs.getInt(4));
                    s.setCategoryId(category);

                    list.add(s);
                }
                return list;
            }
        });
    }

    public Double getTotalExpensesByCategory(int userId, int categoryId) { 
        String queryString = "select sum(amount) from personal_expenses where user_id=" + userId + "and category_id=" + categoryId;
        return getAll(queryString);
    }
    
    public Double getTotalExpensesAllTime(User userId) {
        return PersonalExpenseRepository
                .findByUserId(userId)
                .stream()
                .mapToDouble(PersonalExpense::getAmount)
                .sum();
    }

    // find specific personalExpense
    public PersonalExpense findPersonalExpenseByID(int personalExpenseId) {
        PersonalExpense personalExpense = PersonalExpenseRepository.findById(personalExpenseId)
                .orElseThrow(() -> new IllegalStateException(
                        "PersonalExpense with ID " + personalExpenseId + " does not exist"));
        return personalExpense;
    }

    public void addNewPersonalExpense(PersonalExpense PersonalExpense) {
        PersonalExpenseRepository.save(PersonalExpense);
        
        Category category= categoryService.findCategoryByID(PersonalExpense.getCategoryId().getCategoryId());
        
      //add activity log
        ActivityLog log= new ActivityLog();
        
        log.setUserId(PersonalExpense.getUserId());
        log.setMessage("You added a new personal expense of '"+ PersonalExpense.getAmount() +"' under the category: '"+ category.getCategoryName()+"'");
        log.setCreatedAt(new Date(System.currentTimeMillis()));
        activityLogService.addActivityLog(log);
    }

    public void deletePersonalExpense(int PersonalExpenseId) {
        // Retrieve the PersonalExpense entity from the PersonalExpenseRepository
        PersonalExpense personalExpense = PersonalExpenseRepository.findById(PersonalExpenseId)
                .orElseThrow(() -> new IllegalStateException(
                        "PersonalExpense with id " + PersonalExpenseId + " does not exist"));
        PersonalExpenseRepository.delete(personalExpense);
    }

    @Transactional
    public void updatePersonalExpense(PersonalExpense PersonalExpense) {
        PersonalExpense expense = PersonalExpenseRepository.findById(PersonalExpense.getPersonalExpenseId())
                .orElseThrow(() -> new IllegalStateException(
                        "PersonalExpense with id " + PersonalExpense.getPersonalExpenseId() + " does not exist"));

        if (PersonalExpense.getAmount() != 0 && !Objects.equals(PersonalExpense.getAmount(), expense.getAmount())) {
            expense.setAmount(PersonalExpense.getAmount());
        }

        if (PersonalExpense.getDescription() != null && PersonalExpense.getDescription().length() > 0
                && !Objects.equals(PersonalExpense.getDescription(), expense.getDescription())) {
            expense.setDescription(PersonalExpense.getDescription());
        }

        if (PersonalExpense.getCategoryId() != null
                && !Objects.equals(PersonalExpense.getCategoryId(), expense.getCategoryId())) {
            expense.setCategoryId(PersonalExpense.getCategoryId());
        }
    }
}
