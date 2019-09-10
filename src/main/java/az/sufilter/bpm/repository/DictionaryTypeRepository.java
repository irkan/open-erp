package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.DictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DictionaryTypeRepository extends JpaRepository<DictionaryType, Integer> {
    DictionaryType findByAttr1(String attr1);
}