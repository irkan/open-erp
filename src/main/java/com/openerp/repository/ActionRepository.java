package com.openerp.repository;

import com.openerp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_Active(int id, boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndActionOrderByIdDesc(int id, boolean inventoryActive, Dictionary action);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndEmployee_Id(boolean inventoryActive, int employeeId);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndAction_Attr1AndAction_DictionaryType_Attr1(boolean inventoryActive, String attr1, String typeAttr1);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndAction_Attr1AndAction_DictionaryType_Attr1AndEmployee(boolean inventoryActive, String attr1, String typeAttr1, Employee employee);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndAction_Attr1AndAction_DictionaryType_Attr1AndOrganization(boolean inventoryActive, String attr1, String typeAttr1, Organization organization);
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndOrganization(int id, boolean inventoryActive, Organization organization);
    List<Action> getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndFromOrganizationAndApproveFalse(int id, boolean inventoryActive, Organization organization);
    List<Action> getActionsByActionAndInventoryAndInventory_Active(Dictionary action, Inventory inventory, boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_Active(boolean inventoryActive);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndOrganization(boolean inventoryActive, Organization organization);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndFromOrganizationAndApproveFalse(boolean inventoryActive, Organization organization);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndOrganizationAndInventory(boolean inventoryActive, Organization organization, Inventory inventory);
    List<Action> getActionsByActiveTrueAndInventory_BarcodeAndEmployeeAndInventory_ActiveAndAction_Attr1AndAmountGreaterThan(String barcode, Employee employee, boolean active, String attr1, int amount);
    List<Action> getActionsByActiveTrueAndInventory_ActiveAndInventoryAndEmployeeAndAction_Attr1AndAmountGreaterThanOrderById(boolean active, Inventory inventory, Employee employee, String actionAttribute, int amount);
    Action getActionById(int id);
}