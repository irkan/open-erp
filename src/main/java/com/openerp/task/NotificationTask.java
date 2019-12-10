package com.openerp.task;

import com.openerp.entity.Notification;
import com.openerp.repository.CurrencyRateRepository;
import com.openerp.repository.DictionaryRepository;
import com.openerp.repository.NotificationRepository;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class NotificationTask {
    private static final Logger log = Logger.getLogger(NotificationTask.class);

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(fixedDelay = 30000)
    public void email() {
        try{
            log.info("Email Notification Task Start");
            //System.setProperty("java.net.useSystemProxies", "true");
            for(Notification notification:
                    notificationRepository.getNotificationsByActiveTrueAndSentFalseAndType_Attr1AndType_DictionaryType_Attr1AndType_Active(
                            "email", "notification", true
                    )){
                try {
                    MimeMessage msg = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                    helper.setTo(notification.getTo());
                    helper.setSubject(notification.getSubject());
                    helper.setText(notification.getMessage(), true);
                    javaMailSender.send(msg);

                    notification.setSent(true);
                    notification.setSendingDate(new Date());
                    notificationRepository.save(notification);
                } catch (Exception e){
                    e.printStackTrace();
                    log.error(e);
                }
            }
            log.info("Email Notification Task End");
        } catch (Exception e){
            log.error(e);
        }
    }

    @Scheduled(fixedDelay = 600000)
    public void sms() {
        try{
            log.info("SMS Notification Task Start");
            for(Notification notification:
                    notificationRepository.getNotificationsByActiveTrueAndSentFalseAndType_Attr1AndType_DictionaryType_Attr1AndType_Active(
                            "sms", "notification", true
                    )){
            }
            log.info("SMS Notification Task End");
        } catch (Exception e){
            log.error(e);
        }
    }
}
