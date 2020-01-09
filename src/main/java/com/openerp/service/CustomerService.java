package com.openerp.service;

import com.openerp.entity.Customer;
import com.openerp.entity.Employee;
import com.openerp.repository.CustomerRepository;
import com.openerp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Page<Customer> findAll(Customer customer, Pageable pageable){
        return customerRepository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(customer.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), customer.getId())));
                }
                if(customer.getOrganization()!=null && customer.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), customer.getOrganization().getId())));
                }
                if(customer.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), customer.getActive())));
                }
                if(customer.getPerson()!=null && customer.getPerson().getFirstName()!=null && customer.getPerson().getFirstName().trim().length()>0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("firstName"), "%"+customer.getPerson().getFirstName()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getLastName()!=null && customer.getPerson().getLastName().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("lastName"), "%"+customer.getPerson().getLastName()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getIdCardSerialNumber()!=null && customer.getPerson().getIdCardSerialNumber().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("idCardSerialNumber"), "%"+customer.getPerson().getIdCardSerialNumber()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getIdCardPinCode()!=null && customer.getPerson().getIdCardPinCode().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("idCardPinCode"), "%"+customer.getPerson().getIdCardPinCode()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getBirthday()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("person").get("birthday"), customer.getPerson().getBirthday())));
                }
                if(customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getEmail()!=null && customer.getPerson().getContact().getEmail().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("contact").get("email"), "%"+customer.getPerson().getContact().getEmail()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getMobilePhone()!=null && customer.getPerson().getContact().getMobilePhone().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("contact").get("mobilePhone"), "%"+customer.getPerson().getContact().getMobilePhone()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getHomePhone()!=null && customer.getPerson().getContact().getHomePhone().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("contact").get("homePhone"), "%"+customer.getPerson().getContact().getHomePhone()+"%")));
                }
                if(customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getCity()!=null && customer.getPerson().getContact().getCity().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("person").get("contact").get("city"), customer.getPerson().getContact().getCity().getId())));
                }
                if(customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getAddress()!=null && customer.getPerson().getContact().getAddress().trim().length()>0){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("person").get("contact").get("address"), "%"+customer.getPerson().getContact().getAddress()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
