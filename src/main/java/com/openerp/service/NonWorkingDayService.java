package com.openerp.service;

import com.openerp.entity.NonWorkingDay;
import com.openerp.entity.Transaction;
import com.openerp.repository.NonWorkingDayRepository;
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
public class NonWorkingDayService {
    @Autowired
    NonWorkingDayRepository nonWorkingDayRepository;

    public Page<NonWorkingDay> findAll(NonWorkingDay nonWorkingDay, Pageable pageable){
        return nonWorkingDayRepository.findAll(new Specification<NonWorkingDay>() {
            @Override
            public Predicate toPredicate(Root<NonWorkingDay> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(nonWorkingDay.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), nonWorkingDay.getId())));
                }
                if(nonWorkingDay.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), nonWorkingDay.getActive())));
                }
                if(nonWorkingDay.getIdentifier()!=null && !nonWorkingDay.getIdentifier().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identifier"), nonWorkingDay.getIdentifier())));
                }
                if(nonWorkingDay.getDescription()!=null && !nonWorkingDay.getDescription().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("description"), nonWorkingDay.getDescription())));
                }
                if(nonWorkingDay.getNonWorkingDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("nonWorkingDate"), nonWorkingDay.getNonWorkingDateFrom())));
                }
                if(nonWorkingDay.getNonWorkingDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("nonWorkingDate"), nonWorkingDay.getNonWorkingDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
