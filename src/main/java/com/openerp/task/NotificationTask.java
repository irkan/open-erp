package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.entity.Log;
import com.openerp.entity.Notification;
import com.openerp.repository.CurrencyRateRepository;
import com.openerp.repository.DictionaryRepository;
import com.openerp.repository.LogRepository;
import com.openerp.repository.NotificationRepository;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NotificationTask {
    private static final Logger log = Logger.getLogger(NotificationTask.class);

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    LogRepository logRepository;

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void email() {
        try{
            List<Notification> emails = notificationRepository.getNotificationsByActiveTrueAndSendAndType_Attr1AndType_DictionaryType_Attr1AndType_Active(0,"email", "notification", true);
            for(Notification notification: emails){
                try {
                    MimeMessage msg = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                    helper.setTo(notification.getTo());
                    helper.setSubject(notification.getSubject());
                    helper.setText(notification.getMessage(), true);
                    javaMailSender.send(msg);

                    Log logObject = new Log("notification", "send", null, notification.toString(), "", "Email göndərildi: " + notification.getTo(), UtilJson.toJson(notification));
                    logRepository.save(logObject);

                    notification.setSend(1);
                    notification.setSendingDate(new Date());
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
                notificationRepository.save(notification);
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(fixedDelay = 180000, initialDelay = 15000)
    public void checkEmailError() {
        try{
            List<Notification> emails = notificationRepository.getNotificationsByActiveTrueAndSendAndType_Attr1AndType_DictionaryType_Attr1AndType_Active(0,"email", "notification", true);
            for(Notification notification: emails){
                try {
                    MimeMessage msg = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                    helper.setTo(notification.getTo());
                    helper.setSubject(notification.getSubject());
                    helper.setText(notification.getMessage(), true);
                    javaMailSender.send(msg);

                    Log logObject = new Log("notification", "send", null, notification.toString(), "", "Email göndərildi: " + notification.getTo(), UtilJson.toJson(notification));
                    logRepository.save(logObject);

                    notification.setSend(1);
                    notification.setSendingDate(new Date());
                } catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                    notification.setSend(2);
                    Log logObject = new Log("error", "notification", "send", null, notification.toString(), "", "Email göndərilmədi: " + notification.getTo() + e.getMessage(), UtilJson.toJson(notification));
                    logRepository.save(logObject);
                }
                notificationRepository.save(notification);
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
/*
    @Scheduled(fixedDelay = 600000)
    public void sms() {
        try{
            log.info("SMS Notification Task Start");
            List<Notification> smses = notificationRepository.getNotificationsByActiveTrueAndSentFalseAndType_Attr1AndType_DictionaryType_Attr1AndType_Active("sms", "notification", true);
            for(Notification notification: smses){
            }
            log.info("SMS Notification Task End");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }*/
}
