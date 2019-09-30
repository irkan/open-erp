package com.openerp.repository;

import com.openerp.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    List<Action> getActionsByActiveTrueAndInventory_Id(int id);
}