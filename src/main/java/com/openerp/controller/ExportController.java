package com.openerp.controller;

import com.openerp.util.Docx4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/export")
public class ExportController extends SkeletonController {

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping(value = "/vacation/order", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param) throws IOException, Docx4JException {

        File file = Docx4j.generateDocument(resourceLoader.getResource("classpath:/template/vacation-order.docx"),
                "test dataaaaaaaaaaaa", "");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                //.headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
