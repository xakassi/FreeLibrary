package library.services.SpringDataImpl;

import library.model.Notification;
import library.model.User;
import library.repository.NotificationRepository;
import library.services.additional.IterableToCollectionMaker;
import library.services.interfaces.NotificationService;

import java.sql.Date;
import java.util.List;

public class SpringDataNotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;

    public SpringDataNotificationServiceImpl(
            NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getNotificationsForUser(int userID) {
        return IterableToCollectionMaker.iterableToList(
                notificationRepository.getNotificationsByUserId(userID));
    }

    @Override
    public Notification createNotification(User user, String text) {
        Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        Notification notification = new Notification(user, text, sqlDate);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationByID(int id) {
        return notificationRepository.getNotificationById(id);
    }

    @Override
    public void deleteNotification(Notification notification) {
        notificationRepository.delete(notification);
    }
}
