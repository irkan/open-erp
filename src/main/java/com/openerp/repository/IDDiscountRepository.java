package com.openerp.repository;

import com.openerp.entity.IDDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDDiscountRepository extends JpaRepository<IDDiscount, Integer> {
    List<IDDiscount> getIDDiscountsByActiveTrue();
    IDDiscount getIDDiscountById(int id);
    List<IDDiscount> getIDDiscountByCode(String code);
}