package library.services.JDBCImpl;

import library.model.Notification;
import library.services.interfaces.DBService;
import library.services.interfaces.NotificationService;

import java.sql.Date;
import java.util.List;

public class JDBCNotificationServiceImpl implements NotificationService {
    private DBService dbService;

    public JDBCNotificationServiceImpl(DBService dbService) {
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
