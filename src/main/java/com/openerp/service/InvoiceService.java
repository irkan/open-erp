package com.openerp.service;

import com.openerp.entity.Invoice;
import com.openerp.entity.Sales;
import com.openerp.repository.InvoiceRepository;
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
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    public Page<Invoice> findAll(Invoice invoice, Pageable pageable){
        return invoiceRepository.findAll(new Specification<Invoice>() {
            @Override
            public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(invoice.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), invoice.getId())));
                }
                if(invoice.getOrganization()!=null && invoice.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), invoice.getOrganization().getId())));
                }
                if(invoice.getSales()!=null && invoice.getSales().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales"), invoice.getSales().getId())));
                }
                if(invoice.getSales()!=null && invoice.getSales().getCustomer()!=null && invoice.getSales().getCustomer().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales").get("customer"), invoice.getSales().getCustomer().getId())));
                }
                if(invoice.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), invoice.getActive())));
                }
                if(invoice.getApprove()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("approve"), invoice.getApprove())));
                }
                if(invoice.getPaymentChannel()!=null && invoice.getPaymentChannel().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("paymentChannel"), invoice.getPaymentChannel().getId())));
                }
                if(invoice.getInvoiceDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("invoiceDate"), invoice.getInvoiceDateFrom())));
                }
                if(invoice.getInvoiceDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("invoiceDate"), invoice.getInvoiceDate())));
                }
                if(invoice.getPriceFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), invoice.getPriceFrom())));
                }
                if(invoice.getPrice()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), invoice.getPrice())));
                }
                if(invoice.getDescription()!=null && !invoice.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+invoice.getDescription()+"%")));
                }
                if(invoice.getChannelReferenceCode()!=null && !invoice.getChannelReferenceCode().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("channelReferenceCode"), "%"+invoice.getChannelReferenceCode()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
