package com.openerp.datatable;

import com.openerp.entity.CurrencyRate;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface CurrencyRateDatatable extends DataTablesRepository<CurrencyRate, Integer> {
    @Override
    DataTablesOutput<CurrencyRate> findAll(DataTablesInput dataTablesInput);
}