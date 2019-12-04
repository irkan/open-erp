package com.openerp.util;

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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.text.pdf.BaseFont;
import com.openerp.entity.Invoice;
import com.openerp.repository.ConfigurationRepository;
import org.apache.log4j.Logger;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

public class GeneratePDFFile {
    private static final Logger log = Logger.getLogger(GeneratePDFFile.class);

    public static File generateInvoice(List<Invoice> invoices, ResourceLoader resourceLoader, ConfigurationRepository configurationRepository) throws FileNotFoundException {
        String invoiceCount = configurationRepository.getConfigurationByKey("invoice_count").getAttribute();
        String companyName = configurationRepository.getConfigurationByKey("company_name").getAttribute();
        String companyHotLine = configurationRepository.getConfigurationByKey("company_hot_line").getAttribute();
        String companyTelephone = configurationRepository.getConfigurationByKey("company_telephone").getAttribute();
        String companyMobile = configurationRepository.getConfigurationByKey("company_mobile").getAttribute();
        File file = new File("invoice-"+(new Date()).getTime() + ".pdf");
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file.getPath()));
        try(Document document = new Document(pdfDocument, PageSize.A4)){
            document.setMargins(20, 20, 20, 20);
            PdfFont times = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/times.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            times.setSubset(true);
            PdfFont timesbd = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/timesbd.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesbd.setSubset(true);
            PdfFont timesbi = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/timesbi.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesbi.setSubset(true);
            PdfFont timesi = PdfFontFactory.createFont(resourceLoader.getResource("classpath:/fonts/timesi.ttf").getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            timesi.setSubset(true);
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
                        Table table = new Table(4);
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
                        cell = new Cell(3, 1)
                                .setFont(timesbd)
                                .setFontSize(28)
                                .setRotationAngle(-85)
                                .setFontColor(DeviceRgb.BLUE)
                                .setBorder(Border.NO_BORDER)
                                .add(new Paragraph("ÖDƏNİLİB"))
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

                        if(invoice.getSales().getCustomer().getPerson().getContact().getLivingAddress().trim().length()>0){
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

                        document.add(table);
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        } catch (Exception e){
            log.error(e);
        }
        return file;
    }
}