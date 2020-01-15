package com.openerp.service;

import com.openerp.entity.Demonstration;
import com.openerp.entity.Transaction;
import com.openerp.repository.DemonstrationRepository;
import com.openerp.repository.TransactionRepository;
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
public class DemonstrationService {
    @Autowired
    DemonstrationRepository demonstrationRepository;

    public Page<Demonstration> findAll(Demonstration demonstration, Pageable pageable){
        return demonstrationRepository.findAll(new Specification<Demonstration>() {
            @Override
            public Predicate toPredicate(Root<Demonstration> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(demonstration.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), demonstration.getId())));
                }
                if(demonstration.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), demonstration.getActive())));
                }
                if(demonstration.getOrganization()!=null && demonstration.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), demonstration.getOrganization().getId())));
                }
                if(demonstration.getEmployee()!=null && demonstration.getEmployee().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), demonstration.getEmployee().getId())));
                }
                if(demonstration.getDemonstrateDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("demonstrateDate"), demonstration.getDemonstrateDateFrom())));
                }
                if(demonstration.getDemonstrateDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("demonstrateDate"), demonstration.getDemonstrateDate())));
                }
                if(demonstration.getDescription()!=null && !demonstration.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+demonstration.getDescription()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
