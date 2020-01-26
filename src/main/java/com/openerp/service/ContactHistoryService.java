package com.openerp.service;

import com.openerp.entity.ContactHistory;
import com.openerp.entity.Log;
import com.openerp.repository.ContactHistoryRepository;
import com.openerp.repository.LogRepository;
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
public class ContactHistoryService {
    @Autowired
    ContactHistoryRepository contactHistoryRepository;

    public Page<ContactHistory> findAll(ContactHistory contactHistory, Pageable pageable){
        return contactHistoryRepository.findAll(new Specification<ContactHistory>() {
            @Override
            public Predicate toPredicate(Root<ContactHistory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(contactHistory.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), contactHistory.getId())));
                }
                if(contactHistory.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), contactHistory.getActive())));
                }
                if(contactHistory.getSales()!=null && contactHistory.getSales().getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales"), contactHistory.getSales().getId())));
                }
                if(contactHistory.getContactChannel()!=null && contactHistory.getContactChannel().getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("contactChannel"), contactHistory.getContactChannel().getId())));
                }
                if(contactHistory.getDescription()!=null && !contactHistory.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+contactHistory.getDescription()+"%")));
                }
                if(contactHistory.getNextContactDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("nextContactDate"), contactHistory.getNextContactDateFrom())));
                }
                if(contactHistory.getNextContactDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("nextContactDate"), contactHistory.getNextContactDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
