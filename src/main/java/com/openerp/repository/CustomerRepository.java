package com.openerp.repository;

import com.openerp.entity.Customer;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer getCustomerByIdAndActiveTrue(int id);
    List<Customer> getCustomersByActiveTrue();
}