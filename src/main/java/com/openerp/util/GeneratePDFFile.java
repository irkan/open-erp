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
import com.openerp.entity.Dictionary;
import com.openerp.entity.Invoice;
import com.openerp.repository.ConfigurationRepository;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

public class GeneratePDFFile {
    private static final Logger log = Logger.getLogger(GeneratePDFFile.class);

    public static File generateInvoice(List<Invoice> invoices, ConfigurationRepository configurationRepository, List<Dictionary> months) throws FileNotFoundException {
        String invoiceCount = configurationRepository.getConfigurationByKey("invoice_count").getAttribute();
        String companyName = configurationRepository.getConfigurationByKey("company_name").getAttribute();
        String companyHotLine = configurationRepository.getConfigurationByKey("company_hot_line").getAttribute();
        String companyTelephone = configurationRepository.getConfigurationByKey("company_telephone").getAttribute();
        String companyMobile = configurationRepository.getConfigurationByKey("company_mobile").getAttribute();
        File file = new File("invoice-"+(new Date()).getTime() + ".pdf");
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file.getPath()));
        try(Document document = new Document(pdfDocument, PageSize.A4)){
            document.setMargins(20, 20, 20, 20);
            ClassPathResource timesCPR = new ClassPathResource("/fonts/times.ttf");
            PdfFont times = PdfFontFactory.createFont(timesCPR.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            times.setSubset(true);
            ClassPathResource timesbdCPR = new ClassPathResource("/fonts/timesbd.ttf");
            PdfFont timesbd = PdfFontFactory.createFont(timesbdCPR.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesbd.setSubset(true);
            ClassPathResource timesbiCPR = new ClassPathResource("/fonts/timesbi.ttf");
            PdfFont timesbi = PdfFontFactory.createFont(timesbiCPR.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesbi.setSubset(true);
            ClassPathResource timesiCPR = new ClassPathResource("/fonts/timesi.ttf");
            PdfFont timesi = PdfFontFactory.createFont(timesiCPR.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesi.setSubset(true);
            ClassPathResource paidCPR = new ClassPathResource("classpath:/stamp/paid.png");
            ImageData stampData = ImageDataFactory.create(paidCPR.getPath());
            Image stamp = new Image(stampData);
            if(invoices.size()==0){
                Paragraph paragraph = new Paragraph("Hesab-faktura tapılmadı!");
                paragraph.setFont(timesbd);
                paragraph.setFontSize(30);
                paragraph.setTextAlignment(TextAlignment.CENTER);
                document.add(paragraph);
            }
            for(Invoice invoice: invoices){
                for(int i=0; i<Integer.parseInt(invoiceCount); i++){
                    try {
                        Table table=null;
                        if(!invoice.getSales().getService()) {
                            table = new Table(4);
                            table.setWidth(UnitValue.createPercentValue(100));
                            Cell cell = new Cell(2, 1)
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbi)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(companyName));
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Mədaxil qəbzi № " + invoice.getId()))
                                    .setWordSpacing(3)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                    .setTextAlignment(TextAlignment.CENTER);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Tarix:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(DateUtility.getFormattedDate(invoice.getInvoiceDate())))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
                            table.addCell(cell);
                            cell = new Cell(2, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .setFontSize(18)
                                    .add(new Paragraph(DateUtility.getFormattedDate(invoice.getInvoiceDate())))
                                    .setVerticalAlignment(VerticalAlignment.TOP)
                                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setPaddingRight(20);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbi)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getOrganization().getName()));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Tarix:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("\" ___ \" _________   " + ((new Date()).getYear()+1900) + " il"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("S.A.A:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getSales().getCustomer().getPerson().getLastName() + " " + invoice.getSales().getCustomer().getPerson().getFirstName() + " " + invoice.getSales().getCustomer().getPerson().getFatherName()))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(4, 1)
                                    .setBorder(Border.NO_BORDER)
                                    .add(stamp.setRotationAngle(-85).setWidth(100))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);

                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Ünvan:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getSales().getCustomer().getPerson().getContact().getCity().getName() + ", " + invoice.getSales().getCustomer().getPerson().getContact().getAddress()))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);

                            String mobilePhone = invoice.getSales().getCustomer().getPerson().getContact().getMobilePhone();
                            String homePhone = invoice.getSales().getCustomer().getPerson().getContact().getHomePhone();
                            String phone = (mobilePhone.trim().length()>0?mobilePhone:"-") + " // " + (homePhone.trim().length()>0?homePhone:"-");
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Telefon:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(phone))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);

                            if(invoice.getSales().getCustomer().getPerson().getContact().getLivingAddress()!=null &&
                                    invoice.getSales().getCustomer().getPerson().getContact().getLivingAddress().trim().length()>0){
                                cell = new Cell(1, 1)
                                        .setFont(timesbd)
                                        .setBorder(Border.NO_BORDER)
                                        .add(new Paragraph("Yaşayış ünvanı:"))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                        .setTextAlignment(TextAlignment.RIGHT);
                                table.addCell(cell);
                                cell = new Cell(1, 3)
                                        .setFont(timesbd)
                                        .setBorder(Border.NO_BORDER)
                                        .add(new Paragraph(invoice.getSales().getCustomer().getPerson().getContact().getLivingCity().getName() + ", " + invoice.getSales().getCustomer().getPerson().getContact().getLivingAddress()))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                                table.addCell(cell);
                            }

                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Ödəniş təyinatı:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("aylıq"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Məbləğ:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            IntToAZE intToAZE = new IntToAZE();
                            cell = new Cell(1, 3)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getPrice() + " AZN" + " ("+Util.getDigitInWord(String.valueOf(invoice.getPrice()))+")"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Ödənildi:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("___________________________________________________"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setPaddingLeft(60);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("İcraçı:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .setUnderline()
                                    .add(new Paragraph(invoice.getCollector()!=null?"  " + invoice.getCollector().getPerson().getLastName() + " " + invoice.getCollector().getPerson().getFirstName() + " " + invoice.getCollector().getPerson().getFatherName() + "  ":"_______________________"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Müştəri:"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                                    .setTextAlignment(TextAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .setUnderline()
                                    .add(new Paragraph(invoice.getCollector()!=null?"  " + invoice.getSales().getCustomer().getPerson().getLastName() + " " + invoice.getSales().getCustomer().getPerson().getFirstName() + " " + invoice.getSales().getCustomer().getPerson().getFatherName() + "  ":"_______________________"))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(""))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Tel: " + companyTelephone))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Mob: " + companyMobile))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Qaynar xətt: " + companyHotLine))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                            cell = new Cell(1, 4)
                                    .setFont(timesbd)
                                    .setFontColor(DeviceRgb.RED)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new LineSeparator(new DottedLine(1, 25)).setMarginTop(5).setMarginBottom(5))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);
                        } else {
                            table = new Table(9);
                            table.setWidth(UnitValue.createPercentValue(100));
                            Cell cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(companyName));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getOrganization().getContact().getCity().getName()));
                            table.addCell(cell);
                            cell = new Cell(1, 7)
                                    .setWidth(UnitValue.createPercentValue(75))
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Tarix:"));
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setWidth(UnitValue.createPercentValue(25))
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbi)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(""));
                            table.addCell(cell);
                            Paragraph invoiceDate = new Paragraph("\" "+invoice.getInvoiceDate().getDate()+" \"  \" "+DateUtility.getMonthTextAZE(months, (invoice.getInvoiceDate().getMonth()+1))+" \"  " + (invoice.getInvoiceDate().getYear()+1900) + " ci il.");
                            cell = new Cell(1, 7)
                                    .setWidth(UnitValue.createPercentValue(75))
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(invoiceDate);
                            table.addCell(cell);
                            cell = new Cell(3, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                    .setFont(timesbd)
                                    .setFontSize(11)
                                    .setRotationAngle(1.5707963268)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Müştəri"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("S.A.A:"));
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getSales().getCustomer().getPerson().getFullName()));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Məbləğ:"));
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getPrice() + " AZN" + " ("+Util.getDigitInWord(String.valueOf(invoice.getPrice()))+")"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(20))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Ünvan:"));
                            table.addCell(cell);
                            cell = new Cell(1, 4)
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getSales().getCustomer().getPerson().getContact().getCity().getName() + ", " + invoice.getSales().getCustomer().getPerson().getContact().getAddress()));
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("______________________________"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Əlaqə:"));
                            table.addCell(cell);
                            cell = new Cell(1, 4)
                                    .setWidth(UnitValue.createPercentValue(75))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(invoice.getSales().getCustomer().getPerson().getContact().getMobilePhone() + " " + (invoice.getSales().getCustomer().getPerson().getContact().getHomePhone()!=null?invoice.getSales().getCustomer().getPerson().getContact().getHomePhone():"")+" " + (invoice.getSales().getCustomer().getPerson().getContact().getRelationalPhoneNumber1()!=null?invoice.getSales().getCustomer().getPerson().getContact().getRelationalPhoneNumber1():"")));
                            table.addCell(cell);
                            cell = new Cell(1, 3)
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("______________________________"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("F1"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Membran"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("A/T rele"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Vintil"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Monometr"));
                            table.addCell(cell);

                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("F2"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Hidrofor"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Y/T rele"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Şlanq/K"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Salnik"));
                            table.addCell(cell);

                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("F3"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Adapter"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Dirsək/çən"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Şlanq/O"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("4 qulaq"));
                            table.addCell(cell);

                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("F5"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Kran"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Dirsək/adi"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Smestitel"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("TDS"));
                            table.addCell(cell);

                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("F6"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Flow"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Dirsək/Klapan"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("*"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setWidth(UnitValue.createPercentValue(14))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(times)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Çən"));
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setWidth(UnitValue.createPercentValue(11))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(""));
                            table.addCell(cell);

                            cell = new Cell(1, 2)
                                    .setWidth(UnitValue.createPercentValue(25))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setMarginTop(30)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("Müştəri / İmza"));
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setWidth(UnitValue.createPercentValue(25))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setMarginTop(30)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("____________________"));
                            table.addCell(cell);
                            cell = new Cell(1, 1)
                                    .setMarginTop(30)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph(""));
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setWidth(UnitValue.createPercentValue(25))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setMarginTop(30)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("İcraçı / İmza"));
                            table.addCell(cell);
                            cell = new Cell(1, 2)
                                    .setWidth(UnitValue.createPercentValue(25))
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setMarginTop(30)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFont(timesbd)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new Paragraph("____________________"));
                            table.addCell(cell);

                            cell = new Cell(1, 9)
                                    .setFont(timesbd)
                                    .setFontColor(DeviceRgb.RED)
                                    .setBorder(Border.NO_BORDER)
                                    .add(new LineSeparator(new DottedLine(1, 25)).setMarginTop(5).setMarginBottom(5))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);
                            table.addCell(cell);

                        }
                        document.add(table);
                    } catch (Exception e){
                        e.printStackTrace();
                        log.error(e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return file;
    }
}