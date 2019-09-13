package com.openerp.repository;

import com.openerp.entity.DictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryTypeRepository extends JpaRepository<DictionaryType, Integer> {
    DictionaryType findByAttr1(String attr1);
}