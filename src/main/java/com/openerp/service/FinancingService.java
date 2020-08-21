package com.openerp.service;

import com.openerp.entity.Financing;
import com.openerp.entity.Notification;
import com.openerp.repository.FinancingRepository;
import com.openerp.repository.NotificationRepository;
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
public class FinancingService {
    @Autowired
    FinancingRepository financingRepository;

    public Page<Financing> findAll(Financing financing, Pageable pageable){
        return financingRepository.findAll(new Specification<Financing>() {
            @Override
            public Predicate toPredicate(Root<Financing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(financing.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), financing.getId())));
                }
                if(financing.getOrganization()!=null && financing.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), financing.getOrganization().getId())));
                }
                if(financing.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), financing.getActive())));
                }
                if(financing.getInventory()!=null && financing.getInventory().getBarcode()!=null && !financing.getInventory().getBarcode().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("inventory").get("barcode"), "%"+financing.getInventory().getBarcode()+"%")));
                }
                if(financing.getCurrency()!=null && !financing.getCurrency().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("currency"), "%"+financing.getCurrency()+"%")));
                }
                if(financing.getPriceFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), financing.getPriceFrom())));
                }
                if(financing.getPrice()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), financing.getPrice())));
                }
                if(financing.getSalePriceFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("salePrice"), financing.getSalePriceFrom())));
                }
                if(financing.getSalePrice()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("salePrice"), financing.getSalePrice())));
                }
                if(financing.getFinancingDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("financingDate"), financing.getFinancingDateFrom())));
                }
                if(financing.getFinancingDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("financingDate"), financing.getFinancingDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
