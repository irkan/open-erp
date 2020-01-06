package com.openerp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, basePackages = "com.openerp.datatable")
//@EnableJpaRepositories(basePackages = "com.openerp.datatable", repositoryBaseClass = UserDatatable.class)
public class DataTablesConfiguration {}