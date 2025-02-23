package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.bidwik.bid.Entity.User;
import org.bidwik.bid.Enum.EType;



@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndTypeAndIsRead(User user, EType type, boolean read);
}
