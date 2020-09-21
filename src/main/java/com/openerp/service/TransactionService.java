package com.openerp.service;

import com.openerp.entity.Financing;
import com.openerp.entity.Transaction;
import com.openerp.repository.FinancingRepository;
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
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Page<Transaction> findAll(Transaction transaction, Pageable pageable){
        return transactionRepository.findAll(new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(transaction.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), transaction.getId())));
                }
                if(transaction.getApprove()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("approve"), transaction.getApprove())));
                }
                if(!transaction.getAccountable()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("accountable"), transaction.getAccountable())));
                }
                if(transaction.getOrganization()!=null && transaction.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), transaction.getOrganization().getId())));
                }
                if(transaction.getDescription()!=null && !transaction.getDescription().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+transaction.getDescription()+"%")));
                }
                if(transaction.getCurrency()!=null && !transaction.getCurrency().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("currency"), "%"+transaction.getCurrency()+"%")));
                }
                if(transaction.getPriceFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), transaction.getPriceFrom())));
                }
                if(transaction.getPrice()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), transaction.getPrice())));
                }
                if(transaction.getTransactionDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), transaction.getTransactionDateFrom())));
                }
                if(transaction.getTransactionDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), transaction.getTransactionDate())));
                }
                if(transaction.getAction()!=null && transaction.getAction().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("action"), transaction.getAction().getId())));
                }
                if(transaction.getCategory()!=null && transaction.getCategory().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("category"), transaction.getCategory().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
