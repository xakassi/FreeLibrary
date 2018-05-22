package library.repository;

import library.model.Notification;
import library.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends
        CrudRepository<Notification, Integer> {
    Notification getNotificationById(int id);

    Iterable<Notification> getNotificationsByUserId(int userId);

    void deleteByUser(User user);
}
