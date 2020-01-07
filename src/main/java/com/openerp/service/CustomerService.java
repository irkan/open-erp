package com.openerp.service;

import com.openerp.entity.Customer;
import com.openerp.entity.Employee;
import com.openerp.repository.CustomerRepository;
import com.openerp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> findAll(Customer customer){
        return customerRepository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(customer.getPerson().getFirstName()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("person", JoinType.LEFT).get("firstName"), "%"+customer.getPerson().getFirstName()+"%")));
                }
                if(customer.getPerson().getLastName()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("person", JoinType.LEFT).get("lastName"), "%"+customer.getPerson().getLastName()+"%")));
                }
                if(customer.getPerson().getBirthday()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.join("person", JoinType.LEFT).get("birthday"), customer.getPerson().getBirthday())));
                }
                if(customer.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), customer.getOrganization().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
