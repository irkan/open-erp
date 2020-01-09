package com.openerp.service;

import com.openerp.entity.Advance;
import com.openerp.entity.Transaction;
import com.openerp.repository.AdvanceRepository;
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
public class AdvanceService {
    @Autowired
    AdvanceRepository advanceRepository;

    public Page<Advance> findAll(Advance advance, Pageable pageable){
        return advanceRepository.findAll(new Specification<Advance>() {
            @Override
            public Predicate toPredicate(Root<Advance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(advance.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), advance.getId())));
                }
                if(advance.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), advance.getActive())));
                }
                if(advance.getOrganization()!=null && advance.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), advance.getOrganization().getId())));
                }
                if(advance.getApprove()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("approve"), advance.getApprove())));
                }
                if(advance.getDebt()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("debt"), advance.getDebt())));
                }
                if(advance.getEmployee()!=null && advance.getEmployee().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), advance.getEmployee().getId())));
                }
                if(advance.getPayedFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("payed"), advance.getPayedFrom())));
                }
                if(advance.getPayed()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("payed"), advance.getPayed())));
                }
                if(advance.getAdvanceDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("advanceDate"), advance.getAdvanceDateFrom())));
                }
                if(advance.getAdvanceDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("advanceDate"), advance.getAdvanceDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
