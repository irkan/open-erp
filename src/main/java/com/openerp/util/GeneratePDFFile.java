package com.openerp.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.openerp.entity.Invoice;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

public class GeneratePDFFile {
    private static final Logger log = Logger.getLogger(GeneratePDFFile.class);

    public static File generateInvoice(List<Invoice> invoices) throws FileNotFoundException {
        Document document = new Document();
        File file = new File((new Date()).getTime() + ".pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
            document.open();
            document.add(new Paragraph("A Hello World PDF document."));
            document.close();
            writer.close();
        } catch (Exception e) {
            log.error(e);
        }
        return file;
    }
}
