package com.openerp.service;

import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Vacation;
import com.openerp.repository.BusinessTripRepository;
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
public class BusinessTripService {
    @Autowired
    BusinessTripRepository businessTripRepository;

    public Page<BusinessTrip> findAll(BusinessTrip businessTrip, Pageable pageable){
        return businessTripRepository.findAll(new Specification<BusinessTrip>() {
            @Override
            public Predicate toPredicate(Root<BusinessTrip> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(businessTrip.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), businessTrip.getId())));
                }
                if(businessTrip.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), businessTrip.getActive())));
                }
                if(businessTrip.getOrganization()!=null && businessTrip.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), businessTrip.getOrganization().getId())));
                }
                if(businessTrip.getEmployee()!=null && businessTrip.getEmployee().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), businessTrip.getEmployee().getId())));
                }
                if(businessTrip.getIdentifier()!=null && businessTrip.getIdentifier().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identifier"), businessTrip.getIdentifier().getId())));
                }
                if(businessTrip.getDescription()!=null && !businessTrip.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+businessTrip.getDescription()+"%")));
                }
                if(businessTrip.getStartDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), businessTrip.getStartDate())));
                }
                if(businessTrip.getEndDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), businessTrip.getEndDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
