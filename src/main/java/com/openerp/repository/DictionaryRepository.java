package com.openerp.repository;

import com.openerp.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Integer> {
    List<Dictionary> getDictionariesByActiveTrueAndDictionaryType_Attr1(String attr1);
    List<Dictionary> getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1(String attr1, String attr2);
    List<Dictionary> getDictionariesByDictionaryType_Id(int id);
    Dictionary getDictionaryById(int id);
    List<Dictionary> getDictionariesByActiveTrueAndDictionaryType_Active(boolean dictionaryTypeActive);
    Dictionary getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(String attr1, String typeAttr1);
    List<Dictionary> getDictionariesByActiveTrueAndDictionaryType_Attr1AndNameStartingWith(String typeAttr1, String name);
}