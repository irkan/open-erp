package com.openerp.controller;

import com.openerp.entity.Invoice;
import com.openerp.util.Docx4j;
import com.openerp.util.GeneratePDFFile;
import com.openerp.util.Util;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/export")
public class ExportController extends SkeletonController {

    @Autowired
    ResourceLoader resourceLoader;

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
    public ResponseEntity<Resource> generateInvoice(@RequestParam(name = "data", value = "") String data) throws IOException, Docx4JException {
        List<Integer> invoiceIds = Util.getInvoiceIds(data);
        List<Invoice> invoices = invoiceRepository.getInvoicesByActiveTrueAndApproveTrueAndIdIn(invoiceIds);
        File file = GeneratePDFFile.generateInvoice(invoices);
        InputStreamResource resourceIS = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=invoice-" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .body(resourceIS);
    }
}
