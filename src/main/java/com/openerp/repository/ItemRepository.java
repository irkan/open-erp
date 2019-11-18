package com.openerp.repository;

import com.openerp.entity.Inventory;
import com.openerp.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> getItemsByActiveTrue();
    List<Item> getItemsByActiveTrueAndBarcode(String barcode);
    Item getItemById(int id);
}