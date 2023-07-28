package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Notification;
import com.example.project.Model.User;
import com.example.project.Service.NotificationService;

@RestController
@RequestMapping(path = "api/notification")
public class NotificationController {

    private final NotificationService NotificationService;

    @Autowired
    public NotificationController(NotificationService NotificationService) {
        this.NotificationService= NotificationService;
    }

    @GetMapping("/showUserNotifications/{userId}")
    public List<Notification> getUserNotification(User userId) {
        return NotificationService.getUserNotifications(userId);
    }
    
    @PostMapping("/addNotification")
    public Notification addNotification(@RequestBody Notification notification) {
        return NotificationService.addNotification(notification);
    }
    
    @DeleteMapping(path= "/deleteNotification/{notificationId}")
    public void deleteNotification(@PathVariable("notificationId") int notificationId) {
        NotificationService.deleteNotification(notificationId);
    }
}
