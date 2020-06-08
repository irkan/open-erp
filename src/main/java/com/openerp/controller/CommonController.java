package com.openerp.controller;

import com.openerp.entity.PersonDocument;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/common")
public class CommonController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        return "layout";
    }

    @ResponseBody
    @GetMapping(value = "/api/person/document/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDocument> getPersonDocuments(@PathVariable("id") Integer id){
        try {
            return personDocumentRepository.getPersonDocumentsByPersonAndDocumentType(personRepository.getPersonById(id), dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("id card", "document-type"));
        } catch (Exception e){
            log(null, "error", "common_person_document", "", null, "", "Xəta baş verdi! " + e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
