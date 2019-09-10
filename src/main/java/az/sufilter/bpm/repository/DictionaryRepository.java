package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Integer> {
    List<Dictionary> getDictionariesByDictionaryType_Attr1(String attr1);
}