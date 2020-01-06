package com.openerp.datatable;

import com.openerp.entity.CurrencyRate;
import com.openerp.entity.Employee;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface EmployeeDatatable extends DataTablesRepository<Employee, Integer> {
    @Override
    DataTablesOutput<Employee> findAll(DataTablesInput dataTablesInput);
}