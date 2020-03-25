package com.openerp.service;

import com.openerp.entity.Action;
import com.openerp.entity.Advance;
import com.openerp.repository.ActionRepository;
import com.openerp.repository.AdvanceRepository;
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
public class ActionService {
    @Autowired
    ActionRepository actionRepository;

    public Page<Action> findAll(Action action, Pageable pageable){
        return actionRepository.findAll(new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(action.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), action.getId())));
                }
                if(action.getInventory()!=null && action.getInventory().getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("inventory"), action.getInventory().getId())));
                }
                if(action.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), action.getActive())));
                }
                if(action.getOrganization()!=null && action.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), action.getOrganization().getId())));
                }
                if(action.getEmployee()!=null && action.getEmployee().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), action.getEmployee().getId())));
                }
                if(action.getAction()!=null && action.getAction().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("action"), action.getAction().getId())));
                }
                if(action.getAmountFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), action.getAmountFrom())));
                }
                if(action.getAmount()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), action.getAmount())));
                }
                if(action.getOld()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("old"), action.getOld())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
