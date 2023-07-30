package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.User;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog,Integer> {
    @Query("SELECT m FROM ActivityLog m WHERE m.userId=?1 ORDER by m.logId DESC")
    List<ActivityLog> findUserActivityLogs(User userId);
}
