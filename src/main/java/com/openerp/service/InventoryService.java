package com.openerp.service;

import com.openerp.entity.Action;
import com.openerp.entity.Inventory;
import com.openerp.repository.ActionRepository;
import com.openerp.repository.InventoryRepository;
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
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    public Page<Inventory> findAll(Inventory inventory, Pageable pageable){
        return inventoryRepository.findAll(new Specification<Inventory>() {
            @Override
            public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(inventory.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), inventory.getId())));
                }
                if(inventory.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), inventory.getActive())));
                }
                if(inventory.getOrganization()!=null && inventory.getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), inventory.getOrganization().getId())));
                }
                if(inventory.getGroup()!=null && inventory.getGroup().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("group"), inventory.getGroup().getId())));
                }
                if(inventory.getOld()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("old"), inventory.getOld())));
                }
                if(inventory.getName()!=null && !inventory.getName().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%"+inventory.getName()+"%")));
                }
                if(inventory.getBarcode()!=null && !inventory.getBarcode().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("barcode"), "%"+inventory.getBarcode()+"%")));
                }
                if(inventory.getDescription()!=null && !inventory.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+inventory.getDescription()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
