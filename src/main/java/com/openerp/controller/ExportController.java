package com.openerp.controller;

import com.openerp.util.Docx4j;
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
}
