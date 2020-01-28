package com.openerp.controller;

import com.itextpdf.text.DocumentException;
import com.openerp.entity.*;
import com.openerp.util.*;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/export")
public class ExportController extends SkeletonController {

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public ResponseEntity<Resource> route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        File file = new File("template");
        if(page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            file = ReadWriteExcelFile.dictionaryXLSXFile((List< Dictionary>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)){
            file = ReadWriteExcelFile.dictionaryTypeXLSXFile((List<DictionaryType>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.USER)){
            file = ReadWriteExcelFile.userXLSXFile((List<User>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)){
            file = ReadWriteExcelFile.moduleOperationXLSXFile((List<ModuleOperation>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.MODULE)){
            file = ReadWriteExcelFile.moduleXLSXFile((List<Module>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            file = ReadWriteExcelFile.operationXLSXFile((List<Operation>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CURRENCY_RATE)){
            file = ReadWriteExcelFile.currencyRateXLSXFile((List<CurrencyRate>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.NOTIFICATION)){
            file = ReadWriteExcelFile.notificationRateXLSXFile((Page<Notification>) model.asMap().get(Constants.EXPORTS), page);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.LOG)){
            file = ReadWriteExcelFile.logRateXLSXFile((Page<Log>) model.asMap().get(Constants.EXPORTS), page);
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
        File file = GeneratePDFFile.generateInvoice(invoices, resourceLoader, configurationRepository);
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
}
