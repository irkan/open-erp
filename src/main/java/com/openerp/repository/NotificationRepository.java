package com.openerp.repository;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> getNotificationsByActiveTrueAndSentFalseAndType_Attr1AndType_DictionaryType_Attr1AndType_Active(String attr1, String typeAttr1, boolean active);
    List<Notification> getNotificationsByActiveTrue();
}