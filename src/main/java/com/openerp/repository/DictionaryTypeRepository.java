package com.openerp.repository;

import com.openerp.entity.Dictionary;
import com.openerp.entity.DictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryTypeRepository extends JpaRepository<DictionaryType, Integer> {
    List<DictionaryType> getDictionaryTypesByActiveTrue();
    DictionaryType getDictionaryTypeById(int id);
}