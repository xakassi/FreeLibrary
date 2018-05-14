package library.services;

import library.model.Notification;

import java.sql.Date;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private DBService dbService;

    public NotificationServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }


    @Override
    public List<Notification> getNotificationsForUser(int userID) {
        return dbService.getNotificationsForUser(userID);
    }

    @Override
    public Notification createNotification(int userID, String text) {
        Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        return dbService.createNotificationForUser(userID, text, sqlDate);
    }

    @Override
    public Notification getNotificationByID(int id) {
        return dbService.getNotificationByID(id);
    }

    @Override
    public void deleteNotification(Notification notification) {
        dbService.deleteNotification(notification.getId());
    }
}
