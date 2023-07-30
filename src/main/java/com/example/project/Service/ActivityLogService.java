package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.User;
import com.example.project.Repository.ActivityLogRepository;


@Service
public class ActivityLogService {

    private final ActivityLogRepository logRepository;

    @Autowired
    public ActivityLogService(ActivityLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<ActivityLog> getUserActivityLogs(User userId) {
        return logRepository.findUserActivityLogs(userId);
    }

    public ActivityLog addActivityLog(ActivityLog log) {
        return logRepository.save(log);
    }

    public void deleteActivityLog(int logId) {
        boolean exists = logRepository.existsById(logId);
        if (!exists) {
            throw new IllegalStateException("log with id " + logId + " does not exist");
        }
        logRepository.deleteById(logId);
    }

    @Transactional
    public void updateActivityLog(int logId, String message) {
        ActivityLog log = logRepository.findById(logId)
                .orElseThrow(() -> new IllegalStateException("log with id " + logId + " does not exist"));

        if (message != null && !Objects.equals(log.getMessage(), message)) {
            log.setMessage(message);
        }
    }

}
