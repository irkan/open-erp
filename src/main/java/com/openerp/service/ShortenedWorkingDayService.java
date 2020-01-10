package com.openerp.service;

import com.openerp.entity.NonWorkingDay;
import com.openerp.entity.ShortenedWorkingDay;
import com.openerp.repository.NonWorkingDayRepository;
import com.openerp.repository.ShortenedWorkingDayRepository;
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
public class ShortenedWorkingDayService {
    @Autowired
    ShortenedWorkingDayRepository shortenedWorkingDayRepository;

    public Page<ShortenedWorkingDay> findAll(ShortenedWorkingDay shortenedWorkingDay, Pageable pageable){
        return shortenedWorkingDayRepository.findAll(new Specification<ShortenedWorkingDay>() {
            @Override
            public Predicate toPredicate(Root<ShortenedWorkingDay> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(shortenedWorkingDay.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), shortenedWorkingDay.getId())));
                }
                if(shortenedWorkingDay.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), shortenedWorkingDay.getActive())));
                }
                if(shortenedWorkingDay.getIdentifier()!=null && !shortenedWorkingDay.getIdentifier().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identifier"), shortenedWorkingDay.getIdentifier())));
                }
                if(shortenedWorkingDay.getDescription()!=null && !shortenedWorkingDay.getDescription().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("description"), shortenedWorkingDay.getDescription())));
                }
                if(shortenedWorkingDay.getShortenedTime()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("shortenedTime"), shortenedWorkingDay.getShortenedTime())));
                }
                if(shortenedWorkingDay.getWorkingDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("workingDate"), shortenedWorkingDay.getWorkingDateFrom())));
                }
                if(shortenedWorkingDay.getWorkingDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("workingDate"), shortenedWorkingDay.getWorkingDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
