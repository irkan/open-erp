package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.DictionaryType;
import az.sufilter.bpm.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    List<Module> findAllByModuleIsNull();
}