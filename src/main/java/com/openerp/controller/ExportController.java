package com.openerp.controller;

import com.itextpdf.text.DocumentException;
import com.openerp.domain.SalesSchedule;
import com.openerp.domain.Session;
import com.openerp.entity.*;
import com.openerp.entity.Module;
import com.openerp.util.*;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/export")
public class ExportController extends SkeletonController {

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public ResponseEntity<Resource> route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        File file = new File("template");
        Object object = model.asMap().get(Constants.EXPORTS);
        if(page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            file = ReadWriteExcelFile.dictionaryXLSXFile((List< Dictionary>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)){
            file = ReadWriteExcelFile.dictionaryTypeXLSXFile((List<DictionaryType>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.USER)){
            file = ReadWriteExcelFile.userXLSXFile((List<User>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)){
            file = ReadWriteExcelFile.moduleOperationXLSXFile((List<ModuleOperation>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.MODULE)){
            file = ReadWriteExcelFile.moduleXLSXFile((List<Module>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            file = ReadWriteExcelFile.operationXLSXFile((List<Operation>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CURRENCY_RATE)){
            file = ReadWriteExcelFile.currencyRateXLSXFile((List<CurrencyRate>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.NOTIFICATION)){
            file = ReadWriteExcelFile.notificationRateXLSXFile((Page<Notification>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.LOG)){
            file = ReadWriteExcelFile.logRateXLSXFile((Page<Log>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.ORGANIZATION)){
            file = ReadWriteExcelFile.organizationXLSXFile((List<Organization>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.EMPLOYEE)){
            file = ReadWriteExcelFile.employeeXLSXFile((List<Employee>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.NON_WORKING_DAY)){
            file = ReadWriteExcelFile.nonWorkingDayXLSXFile((Page<NonWorkingDay>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SHORTENED_WORKING_DAY)){
            file = ReadWriteExcelFile.shortenedWorkingDayXLSXFile((Page<ShortenedWorkingDay>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.VACATION)){
            file = ReadWriteExcelFile.vacationXLSXFile((Page<Vacation>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.BUSINESS_TRIP)){
            file = ReadWriteExcelFile.businessTripXLSXFile((Page<BusinessTrip>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.ILLNESS)){
            file = ReadWriteExcelFile.illnessXLSXFile((Page<Illness>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.INVENTORY)){
            file = ReadWriteExcelFile.inventoryXLSXFile((Page<Inventory>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SUPPLIER)){
            file = ReadWriteExcelFile.supplierXLSXFile((List<Supplier>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CONSOLIDATE)){
            file = ReadWriteExcelFile.consolidateXLSXFile((Page<Action>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.TRANSACTION)){
            file = ReadWriteExcelFile.transactionXLSXFile((Page<Transaction>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.ACCOUNT)){
            file = ReadWriteExcelFile.accountXLSXFile((List<Account>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CONSOLIDATE)){
            file = ReadWriteExcelFile.financingXLSXFile((Page<Financing>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.ADVANCE)){
            file = ReadWriteExcelFile.advanceXLSXFile((Page<Advance>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CUSTOMER)){
            file = ReadWriteExcelFile.customerXLSXFile((Page<Customer>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SALES)
                || page.equalsIgnoreCase(Constants.ROUTE.TROUBLED_CUSTOMER)
                || page.equalsIgnoreCase(Constants.ROUTE.PAYMENT_LATENCY)
                || page.equalsIgnoreCase(Constants.ROUTE.COURT)
                || page.equalsIgnoreCase(Constants.ROUTE.EXECUTE)
                || page.equalsIgnoreCase(Constants.ROUTE.SERVICE)){
            file = ReadWriteExcelFile.salesXLSXFile((Page<Sales>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.DEMONSTRATION)){
            file = ReadWriteExcelFile.demonstrationXLSXFile((Page<Demonstration>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SCHEDULE)){
            file = ReadWriteExcelFile.scheduleXLSXFile((Page<SalesSchedule>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.INVOICE)){
            file = ReadWriteExcelFile.invoiceXLSXFile((Page<Invoice>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CONTACT_HISTORY)){
            file = ReadWriteExcelFile.contactHistoryXLSXFile((Page<ContactHistory>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SERVICE_REGULATOR)){
            file = ReadWriteExcelFile.serviceRegulatorXLSXFile((Page<ServiceRegulator>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.ENDPOINT)){
            file = ReadWriteExcelFile.endpointXLSXFile((Page<Endpoint>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SESSION)){
            file = ReadWriteExcelFile.sessionXLSXFile((List<Session>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.APPROVER_EXCEPTION)){
            file = ReadWriteExcelFile.approverExceptionXLSXFile((List<ApproverException>) object, page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.TAX_CONFIGURATION)){
            file = ReadWriteExcelFile.taxConfigurationXLSXFile((List<TaxConfiguration>) object, page);
        }

        InputStreamResource resourceIS = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+(new Date()).getTime() + "-" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resourceIS);
    }

    @RequestMapping(value = "/document/{type}/{page}/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable("type") String type, @PathVariable("page") String page, @PathVariable("id") String id) throws IOException, Docx4JException {
        Resource resource = resourceLoader.getResource("classpath:/template/"+type+"-"+page+".docx");

        File file = Docx4j.generateDocument(resource,
                "test dataaaaaaaaaaaa", "");

        InputStreamResource resourceIS = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + page + "-" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resourceIS);
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    public ResponseEntity<Resource> generateInvoice(@RequestParam(name = "data", value = "") String data) throws IOException, Docx4JException, DocumentException {
        List<Integer> invoiceIds = Util.getInvoiceIds(data);
        List<Invoice> invoices = invoiceRepository.getInvoicesByActiveTrueAndIdIn(invoiceIds);
        List<Dictionary> months = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("month");
        File file = GeneratePDFFile.generateInvoice(invoices, configurationRepository, months);
        InputStreamResource resourceIS = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=invoice-" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .body(resourceIS);
    }

    @RequestMapping(value = "/sale/contract", method = RequestMethod.POST)
    public ResponseEntity<Resource> generateContract(@RequestParam(name = "data", value = "") String data) throws IOException, Docx4JException, DocumentException {

        Sales sales = salesRepository.getSalesByIdAndActiveTrue(Integer.parseInt(data));

        File file = Docx4j.generateContract(resourceLoader,sales,configurationRepository,dictionaryRepository);

        InputStreamResource resourceIS = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=invoice-" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resourceIS);
    }

    @RequestMapping(value = "/migration/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadMigration(@PathVariable("id") Integer id) throws IOException, Docx4JException {
        Migration migration = migrationRepository.getMigrationById(id);
        Path path = Files.write(Paths.get(migration.getFileName()), migration.getFileContent());
        File file = path.toFile();
        InputStreamResource resourceIS = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + migration.getFileName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resourceIS);
    }
}
