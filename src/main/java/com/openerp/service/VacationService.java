package com.openerp.service;

import com.openerp.entity.Demonstration;
import com.openerp.entity.Vacation;
import com.openerp.repository.DemonstrationRepository;
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
public class VacationService {
    @Autowired
    VacationRepository vacationRepository;

    public Page<Vacation> findAll(Vacation vacation, Pageable pageable){
        return vacationRepository.findAll(new Specification<Vacation>() {
            @Override
            public Predicate toPredicate(Root<Vacation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(vacation.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), vacation.getId())));
                }
                if(vacation.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), vacation.getActive())));
                }
                if(vacation.getOrganization()!=null && vacation.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), vacation.getOrganization().getId())));
                }
                if(vacation.getEmployee()!=null && vacation.getEmployee().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), vacation.getEmployee().getId())));
                }
                if(vacation.getIdentifier()!=null && vacation.getIdentifier().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identifier"), vacation.getIdentifier().getId())));
                }
                if(vacation.getDescription()!=null && !vacation.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+vacation.getDescription()+"%")));
                }
                if(vacation.getStartDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), vacation.getStartDate())));
                }
                if(vacation.getEndDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), vacation.getEndDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
