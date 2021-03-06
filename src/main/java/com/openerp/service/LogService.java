package com.openerp.service;

import com.openerp.entity.Customer;
import com.openerp.entity.Log;
import com.openerp.repository.CustomerRepository;
import com.openerp.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public Page<Log> findAll(Log log, Pageable pageable){
        return logRepository.findAll(new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(log.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), log.getId())));
                }
                if(log.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), log.getActive())));
                }
                if(log.getType()!=null && log.getType().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), log.getType())));
                }
                if(log.getTableName()!=null && log.getTableName().trim().length()>0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("tableName"), "%"+log.getTableName()+"%")));
                }
                if(log.getOperation()!=null && log.getOperation().trim().length()>0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("operation"), "%"+log.getOperation()+"%")));
                }
                if(log.getRowId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("rowId"), log.getRowId())));
                }
                if(log.getDescription()!=null && log.getDescription().trim().length()>0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+log.getDescription()+"%")));
                }
                if(log.getUsername()!=null && log.getUsername().trim().length()>0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("username"), "%"+log.getUsername()+"%")));
                }
                if(log.getOperationDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("operationDate"), log.getOperationDateFrom())));
                }
                if(log.getOperationDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("operationDate"), log.getOperationDate())));
                }
                if(log.getEncapsulate()!=null && !log.getEncapsulate().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("encapsulate"), "%"+log.getEncapsulate()+"%")));
                }
                if(log.getJson()!=null && !log.getJson().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("json"), "%"+log.getJson()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
