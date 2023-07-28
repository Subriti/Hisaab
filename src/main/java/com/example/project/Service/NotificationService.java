package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Notification;
import com.example.project.Model.User;
import com.example.project.Repository.NotificationRepository;


@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(User userId) {
        return notificationRepository.findUserNotifications(userId);
    }

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteNotification(int notificationId) {
        boolean exists = notificationRepository.existsById(notificationId);
        if (!exists) {
            throw new IllegalStateException("notification with id " + notificationId + " does not exist");
        }
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public void updateNotification(int notificationId, String title, String message) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalStateException("notification with id " + notificationId + " does not exist"));

        if (title != null && title.length() > 0 && !Objects.equals(notification.getTitle(), title)) {
            notification.setTitle(title);
        }
        if (message != null && !Objects.equals(notification.getMessage(), message)) {
            notification.setMessage(message);
        }
    }

}
