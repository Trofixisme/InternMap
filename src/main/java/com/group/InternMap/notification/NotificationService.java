package com.group.InternMap.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;



@Service
public class NotificationService {
//this smth simple for now . am not done
    SimpMessagingTemplate simpMessagingTemplate;
    NotificationRepo notificationRepo;

    public NotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationRepo notificationRepo) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationRepo = notificationRepo;
    }

    //this is for all users
    public void broadcastNotification(String message) {
        simpMessagingTemplate.convertAndSend("/topic/notifications", message);
    }

    // for spring web sockets it requiers the string username but i noticed in the spring security tje email is identified as the username
    // in customerUserDetails line 34
    public void sendToUser(String userEmail , String message) {
        //saving it to database
        Notification notification = new Notification(userEmail, message);
        notificationRepo.save(notification);
        //sending to user
        simpMessagingTemplate.convertAndSendToUser(userEmail, "/queue/notifications", message);
    }
}
