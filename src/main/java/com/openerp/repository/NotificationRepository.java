package com.openerp.repository;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Log;
import com.openerp.entity.Notification;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer>, JpaSpecificationExecutor<Notification> {
    List<Notification> getNotificationsByActiveTrueAndSendAndType_Attr1AndType_DictionaryType_Attr1AndType_Active(Integer send, String attr1, String typeAttr1, boolean active);
    List<Notification> getNotificationsByActiveTrue();
    List<Notification> getNotificationsByActiveTrueAndOrganization(Organization organization);
    Notification getNotificationById(int id);
}