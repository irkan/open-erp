package com.openerp.repository;

import com.openerp.entity.Action;
import com.openerp.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Integer> {
}