package com.openerp.service;

import com.openerp.entity.Illness;
import com.openerp.entity.Vacation;
import com.openerp.repository.BusinessTripRepository;
import com.openerp.repository.IllnessRepository;
import com.openerp.repository.VacationRepository;
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
public class IllnessService {
    @Autowired
    IllnessRepository illnessRepository;

    public Page<Illness> findAll(Illness illness, Pageable pageable){
        return illnessRepository.findAll(new Specification<Illness>() {
            @Override
            public Predicate toPredicate(Root<Illness> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(illness.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), illness.getId())));
                }
                if(illness.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), illness.getActive())));
                }
                if(illness.getOrganization()!=null && illness.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), illness.getOrganization().getId())));
                }
                if(illness.getEmployee()!=null && illness.getEmployee().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), illness.getEmployee().getId())));
                }
                if(illness.getIdentifier()!=null && illness.getIdentifier().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identifier"), illness.getIdentifier().getId())));
                }
                if(illness.getDescription()!=null && !illness.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+illness.getDescription()+"%")));
                }
                if(illness.getStartDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), illness.getStartDate())));
                }
                if(illness.getEndDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), illness.getEndDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
