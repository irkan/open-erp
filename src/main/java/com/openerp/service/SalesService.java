package com.openerp.service;

import com.openerp.entity.Financing;
import com.openerp.entity.Sales;
import com.openerp.repository.FinancingRepository;
import com.openerp.repository.SalesRepository;
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
public class SalesService {
    @Autowired
    SalesRepository salesRepository;

    public Page<Sales> findAll(Sales sales, Pageable pageable){
        return salesRepository.findAll(new Specification<Sales>() {
            @Override
            public Predicate toPredicate(Root<Sales> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(sales.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), sales.getId())));
                }
                if(sales.getOrganization()!=null && sales.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), sales.getOrganization().getId())));
                }
                if(sales.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), sales.getActive())));
                }
                if(sales.getService()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("service"), sales.getService())));
                }
                if(sales.getSaleDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("saleDate"), sales.getSaleDateFrom())));
                }
                if(sales.getSaleDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("saleDate"), sales.getSaleDate())));
                }
                if(sales.getGuaranteeExpireFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("guaranteeExpire"), sales.getGuaranteeExpireFrom())));
                }
                if(sales.getGuaranteeExpire()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("guaranteeExpire"), sales.getGuaranteeExpire())));
                }
                if(sales.getPayment()!=null && sales.getPayment().getLastPriceFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("payment").get("lastPrice"), sales.getPayment().getLastPriceFrom())));
                }
                if(sales.getPayment()!=null && sales.getPayment().getLastPrice()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("payment").get("lastPrice"), sales.getPayment().getLastPrice())));
                }
                if(sales.getPayment()!=null && sales.getPayment().getPeriod()!=null && sales.getPayment().getPeriod().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("payment").get("period"), sales.getPayment().getPeriod().getId())));
                }
                if(sales.getPayment()!=null && sales.getPayment().getSchedule()!=null && sales.getPayment().getSchedule().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("payment").get("schedule"), sales.getPayment().getSchedule().getId())));
                }
                if(sales.getPayment()!=null && sales.getPayment().getCash()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("payment").get("cash"), sales.getPayment().getCash())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
