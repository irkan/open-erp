package com.openerp.repository;

import com.openerp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_Active(int id, boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndEmployee_Id(boolean inventoryActive, int employeeId);
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndWarehouse(int id, boolean inventoryActive, Organization organization);
    List<Action> getActionsByActionAndInventoryAndInventory_Active(Dictionary action, Inventory inventory, boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_Active(boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndWarehouse(boolean inventoryActive, Organization organization);
    List<Action> getActionsByActiveTrueAndInventory_BarcodeAndEmployeeAndInventory_Active(String barcode, Employee employee, boolean active);
    Action getActionById(int id);
}