package com.openerp.service;

import com.openerp.entity.Sales;
import com.openerp.entity.ServiceTask;
import com.openerp.repository.SalesRepository;
import com.openerp.repository.ServiceTaskRepository;
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
public class ServiceTaskService {
    @Autowired
    ServiceTaskRepository serviceTaskRepository;

    public Page<ServiceTask> findAll(ServiceTask serviceTask, Pageable pageable){
        return serviceTaskRepository.findAll(new Specification<ServiceTask>() {
            @Override
            public Predicate toPredicate(Root<ServiceTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(serviceTask.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), serviceTask.getId())));
                }
                if(serviceTask.getSales()!=null && serviceTask.getSales().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales"), serviceTask.getSales().getId())));
                }
                if(serviceTask.getOrganization()!=null && serviceTask.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), serviceTask.getOrganization().getId())));
                }
                if(serviceTask.getTaskDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("taskDate"), serviceTask.getTaskDateFrom())));
                }
                if(serviceTask.getTaskDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("taskDate"), serviceTask.getTaskDate())));
                }
                if(serviceTask.getDescription()!=null && !serviceTask.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+serviceTask.getDescription()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
