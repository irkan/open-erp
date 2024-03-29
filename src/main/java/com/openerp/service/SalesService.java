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
                if(sales.getCustomer()!=null && sales.getCustomer().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("customer"), sales.getCustomer().getId())));
                }
                if(sales.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), sales.getActive())));
                }
                if(sales.getApprove()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("approve"), !sales.getApprove())));
                }
                if(sales.getSaled()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("saled"), sales.getSaled())));
                }
                if(sales.getReturned()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("returned"), sales.getReturned())));
                }
                if(sales.getService()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("service"), sales.getService())));
                }
                if(sales.getCourt()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("court"), sales.getCourt())));
                }
                if(sales.getExecute()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("execute"), sales.getExecute())));
                }
                if(sales.getExecuteDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("executeDate"), sales.getExecuteDateFrom())));
                }
                if(sales.getExecuteDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("executeDate"), sales.getExecuteDate())));
                }
                if(sales.getSaleDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("saleDate"), sales.getSaleDateFrom())));
                }
                if(sales.getSaleDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("saleDate"), sales.getSaleDate())));
                }
                if(sales.getApproveDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("approveDate"), sales.getApproveDateFrom())));
                }
                if(sales.getApproveDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("approveDate"), sales.getApproveDate())));
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
                if(sales.getCanavasser()!=null && sales.getCanavasser().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("canavasser"), sales.getCanavasser().getId())));
                }
                if(sales.getDealer()!=null && sales.getDealer().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dealer"), sales.getDealer().getId())));
                }
                if(sales.getVanLeader()!=null && sales.getVanLeader().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("vanLeader"), sales.getVanLeader().getId())));
                }
                if(sales.getConsole()!=null && sales.getConsole().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("console"), sales.getConsole().getId())));
                }
                if(sales.getTaxConfiguration()!=null && sales.getTaxConfiguration().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("taxConfiguration"), sales.getTaxConfiguration().getId())));
                }
                if(sales.getCustomer()!=null && sales.getCustomer().getPerson()!=null && sales.getCustomer().getPerson().getFirstName()!=null && !sales.getCustomer().getPerson().getFirstName().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("customer").get("person").get("firstName"), "%"+sales.getCustomer().getPerson().getFirstName()+"%")));
                }
                if(sales.getCustomer()!=null && sales.getCustomer().getPerson()!=null && sales.getCustomer().getPerson().getLastName()!=null && !sales.getCustomer().getPerson().getLastName().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("customer").get("person").get("lastName"), "%"+sales.getCustomer().getPerson().getLastName()+"%")));
                }
                if(sales.getCustomer()!=null &&
                        sales.getCustomer().getPerson()!=null &&
                        sales.getCustomer().getPerson().getContact()!=null &&
                        sales.getCustomer().getPerson().getContact().getTelephone()!=null &&
                        !sales.getCustomer().getPerson().getContact().getTelephone().isEmpty()){
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(root.get("customer").get("person").get("contact").get("mobilePhone"), "%"+sales.getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("customer").get("person").get("contact").get("homePhone"), "%"+sales.getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("customer").get("person").get("contact").get("relationalPhoneNumber1"), "%"+sales.getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("customer").get("person").get("contact").get("relationalPhoneNumber2"), "%"+sales.getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("customer").get("person").get("contact").get("relationalPhoneNumber3"), "%"+sales.getCustomer().getPerson().getContact().getTelephone()+"%")
                    ));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
