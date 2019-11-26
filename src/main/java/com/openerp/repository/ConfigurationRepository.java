package com.openerp.repository;

import com.openerp.entity.Configuration;
import com.openerp.entity.DictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    List<Configuration> getConfigurationsByActiveTrue();
    Configuration getConfigurationById(int id);
    Configuration getConfigurationByKey(String key);
}