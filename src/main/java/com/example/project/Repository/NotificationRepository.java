package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Notification;
import com.example.project.Model.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("SELECT m FROM Notification m WHERE m.recieverId=?1 ORDER by m.notificationId DESC")
    List<Notification> findUserNotifications(User userId);
}
