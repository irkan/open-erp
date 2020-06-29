package com.openerp.repository;

import com.openerp.entity.DictionaryType;
import com.openerp.entity.GlobalConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalConfigurationRepository extends JpaRepository<GlobalConfiguration, Integer> {
    List<GlobalConfiguration> getGlobalConfigurationsByActiveTrue();
    GlobalConfiguration getGlobalConfigurationById(int id);
    GlobalConfiguration getGlobalConfigurationByKey(String key);
}