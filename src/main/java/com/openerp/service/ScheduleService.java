package com.openerp.service;

import com.openerp.domain.Schedule;
import com.openerp.repository.ScheduleRepository;
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
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Page<Schedule> findAll(Schedule schedule, Pageable pageable){
        return scheduleRepository.findAll(new Specification<Schedule>() {
            @Override
            public Predicate toPredicate(Root<Schedule> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(schedule.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), schedule.getId())));
                }
                if(schedule.getPayment()!=null && schedule.getPayment().getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("payment"), schedule.getPayment().getId())));
                }
                if(schedule.getOrganization()!=null && schedule.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), schedule.getOrganization().getId())));
                }
                if(schedule.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), schedule.getActive())));
                }
                if(schedule.getScheduleDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("scheduleDate"), schedule.getScheduleDateFrom())));
                }
                if(schedule.getScheduleDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("scheduleDate"), schedule.getScheduleDate())));
                }
                if(schedule.getAmountFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), schedule.getAmountFrom())));
                }
                if(schedule.getAmount()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), schedule.getAmount())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
