package com.openerp.repository;

import com.openerp.entity.Invoice;
import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
    List<Invoice> getInvoicesByActiveTrueOrderByInvoiceDateDesc();
    List<Invoice> getInvoicesByActiveTrueAndOrganizationOrderByInvoiceDateDesc(Organization organization);
    Invoice getInvoiceById(int id);
    List<Invoice> getInvoicesByActiveTrueAndIdIn(List<Integer> ids);
}