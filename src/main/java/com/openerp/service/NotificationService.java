package com.openerp.service;

import com.openerp.entity.Customer;
import com.openerp.entity.Notification;
import com.openerp.repository.CustomerRepository;
import com.openerp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public Page<Notification> findAll(Notification notification, Pageable pageable){
        return notificationRepository.findAll(new Specification<Notification>() {
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(notification.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), notification.getId())));
                }
                if(notification.getOrganization()!=null && notification.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), notification.getOrganization().getId())));
                }
                if(notification.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), notification.getActive())));
                }
                if(notification.getSend()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("send"), notification.getSend())));
                }
                if(notification.getType()!=null && notification.getType().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), notification.getType().getId())));
                }
                if(notification.getTo()!=null && !notification.getTo().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("to"), "%"+notification.getTo()+"%")));
                }
                if(notification.getSubject()!=null && !notification.getSubject().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subject"), "%"+notification.getSubject()+"%")));
                }
                if(notification.getMessage()!=null && !notification.getMessage().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("message"), "%"+notification.getMessage()+"%")));
                }
                if(notification.getDescription()!=null && !notification.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+notification.getDescription()+"%")));
                }
                if(notification.getSendingDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("sendingDate"), notification.getSendingDateFrom())));
                }
                if(notification.getSendingDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("sendingDate"), notification.getSendingDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
