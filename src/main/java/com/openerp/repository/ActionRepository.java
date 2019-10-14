package com.openerp.repository;

import com.openerp.entity.Action;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_Active(int id, boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndWarehouse(int id, boolean inventoryActive, Organization organization);
    List<Action> getActionsByActiveTrueAndAction_Attr1AndInventory_IdAndInventory_Active(String attr1, int id, boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_Active(boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndWarehouse(boolean inventoryActive, Organization organization);
    Action getActionById(int id);
}