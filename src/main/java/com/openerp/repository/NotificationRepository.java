package com.openerp.repository;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> getNotificationsByActiveTrueAndType(Dictionary dictionary);
    List<Notification> getNotificationsByActiveTrue();
}