package library.services;

import library.model.Notification;

import java.util.List;

public interface NotificationService {
    /**
     * Get all notifications for all users in library.
     * Key = user login, value = notifications for this user
     *
     * @return map of notifications for all users
     */
    //Map<String, List<Notification>> getAllNotifications();

    /**
     * Get all notifications for user {@code userLogin}
     *
     * @param userID is the id of searching user
     * @return list of notifications for this user
     */
    List<Notification> getNotificationsForUser(int userID);

    /**
     * Create notification {@code text} for user {@code userLogin} with current time
     *
     * @param userID is the id of user
     * @param text   is the notice text
     * @return notification with these parameters and id
     */
    Notification createNotification(int userID, String text);

    /**
     * Get notification from list by its id and user login
     *
     * @param id     is the searching id
     * @param userID is the id of user
     * @return notification with this id
     */
    // Notification getNotificationByIDAndUserID(int id, int userID);

    /**
     * Get notification by its id
     *
     * @param id is the searching id
     * @return notification with this id
     */
    Notification getNotificationByID(int id);

    /**
     * Delete the notification {@code notification}
     *
     * @param notification is the deleting notification
     */
    void deleteNotification(Notification notification);
}
