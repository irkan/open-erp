package com.openerp.util;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.log4j.Logger;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class GeneratePDFFile2 {
    private static final Logger log = Logger.getLogger(GeneratePDFFile.class);

    public static File generateInvoice(ResourceLoader resourceLoader) throws FileNotFoundException {
        File file = new File("invoice-"+(new Date()).getTime() + ".pdf");
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file.getPath()));
        Rectangle envelope = new Rectangle(165, 600);
        try(Document document = new Document(pdfDocument, new PageSize(envelope))){
            document.setMargins(6, 6, 6, 6);
            PdfFont times = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/DejaVuSansMono.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            times.setSubset(true);
            PdfFont timesbd = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/timesbd.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesbd.setSubset(true);
            PdfFont timesbi = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/timesbi.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesbi.setSubset(true);
            PdfFont timesi = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/timesi.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesi.setSubset(true);
            String paidFile = resourceLoader.getResource("classpath:/stamp/paid.png").getFile().getPath();
            ImageData stampData = ImageDataFactory.create(paidFile);
            Image stamp = new Image(stampData);
            Paragraph paragraph = new Paragraph("TS adı: OFİS");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(20);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("TS ünvanı: AZ1065 BAKI ŞƏHƏRİ YASAMAL RAYONU MURTUZA MUXTAROV ev.188 m.33");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("VÖ Adı: \"OMNITECH\" MƏHDUD MƏSULİYYƏTLİ CƏMİYYƏTİ");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("VÖEN: 1504533971");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("Obyektin kodu: 1504533971 - 13001");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("SATIŞ ÇEKİ");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(8);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("Çek nömrəsi 20");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginBottom(20);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("Kassa nömrəsi: 001");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Kassir: G_INARA");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Tarix: 2020-02-28");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Saat: 16:30:56");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("***************************************");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(10).setMarginBottom(4);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Malın adı             Vergi növü");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Vahid / Miqdar/Qiymət/Toplam");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("---------------------------------------");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Bijuteriya...................*ƏDV 18%");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("()     1.0     144.00     144.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("***************************************");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(10).setMarginBottom(4);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("ENDIRIM...............0.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("YEKUN MƏBLƏĞ...............144.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("*ƏDV 18%.................144.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("*Toplam ƏDV 18%...........144.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("***************************************");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(10).setMarginBottom(4);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Ödəniş üsulu");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Nağdsız................0.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Nağd...................144.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Bonus................0.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Avans................0.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Kredit................0.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Qaliq qaytarılıb nağd AZN.....0.00");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("***************************************");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(10).setMarginBottom(4);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Gün ərzində vurulmuş çek: 5");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Kassa aparatının modeli: test_all");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Kassa aparatının zavod nömrəsi 1912G57500200008");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Fiscal ID: 2WY63AiGCr1e");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("NMQ qeydiyyat nömrəsi: test_00114");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Token id 00440057314E501220363057");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Çeki yoxlamaq üçün sayt");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginTop(9);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            paragraph = new Paragraph("www.e-kassa.gov.az");
            paragraph.setFont(times);
            paragraph.setFontSize(6.3f);
            paragraph.setMargin(0).setPadding(0).setMultipliedLeading(1.2f).setMarginBottom(7);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

        } catch (Exception e){
            log.error(e);
        }
        return file;
    }
}