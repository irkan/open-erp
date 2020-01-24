package com.openerp.util;

import java.io.*;
import java.util.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Sales;
import com.openerp.repository.ConfigurationRepository;
import com.openerp.repository.DictionaryRepository;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class Docx4j {

    public static void main(String[] args) throws Docx4JException, IOException, JAXBException {

        String filePath = "C:\\Users\\i.ahmadov\\Downloads\\";
        String file = "mezun_emri.docx";

        Docx4j docx4j = new Docx4j();
        WordprocessingMLPackage template = docx4j.getTemplate(filePath+file);

        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<String, String>(){
            {
                this.put("${input_data}", "ChildC5");
            }
            @Override
            public String get(Object key) {
                return super.get(key);
            }
        });

        docx4j.writeDocxToStream(template, filePath+"Hello2.docx");
    }

    public static File generateContract(ResourceLoader resourceLoader, Sales sales, ConfigurationRepository configurationRepository, DictionaryRepository dictionaryRepository) throws IOException, Docx4JException {
        Resource resource = resourceLoader.getResource("classpath:/template/sale-contract.docx");
        List<Dictionary> months = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("month");

        String filePath = "sale-contract-"+sales.getId()+"-"+(new Date()).getTime() +".docx";

        Docx4j docx4j = new Docx4j();
        WordprocessingMLPackage template = docx4j.getTemplate(resource.getFile().getPath());

        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<String, String>(){
            {
                this.put("${contract_number}", String.valueOf(sales.getId()));
                this.put("${contract_date}", DateUtility.generateContractDate(sales.getSaleDate(), months));
                this.put("${consul_full_name}", Util.getPersonLFF(sales.getConsole().getPerson()));
                this.put("${consul_voen}", sales.getConsole().getPerson().getVoen());
                this.put("${consul_id_card_serial_number}", sales.getConsole().getPerson().getIdCardSerialNumber());
               // this.put("${consul_contact_address}", sales.get);
                this.put("${customer_full_name}",  Util.getPersonLFF(sales.getCustomer().getPerson()));
                this.put("${inventory_barcode}",  sales.getSalesInventories().get(0).getInventory().getBarcode());
                this.put("${sale_price}",  String.valueOf(sales.getPayment().getLastPrice()));
                this.put("${sale_price_in_word}",  Util.getDigitInWord(String.valueOf(sales.getPayment().getLastPrice())));
                this.put("${down_payment}",  String.valueOf(sales.getPayment().getDown()));
                this.put("${down_payment_in_word}",  Util.getDigitInWord(String.valueOf(sales.getPayment().getDown())));
                this.put("${remain_payment_price}",  String.valueOf(sales.getPayment().getDown()));
                this.put("${remain_payment_price_in_word}",  Util.getDigitInWord(String.valueOf(sales.getPayment().getDown())));
                //this.put("${period_payment_price}",  String.valueOf(sales.getPayment().getSchedules().get(0).getAmount()));
                //this.put("${period_payment_price_in_word}",  Util.getDigitInWord(String.valueOf(sales.getPayment().getSchedules().get(0).getAmount())));
                this.put("${period_payment_count}",  String.valueOf(sales.getPayment().getPeriod().getName()));
                this.put("${period_payment}",  String.valueOf(sales.getPayment().getSchedule().getName()));
            }
            @Override
            public String get(Object key) {
                return super.get(key);
            }
        });

        docx4j.writeDocxToStream(template, filePath);
        return new File(filePath);
    }

    public static File generateDocument(Resource resource, String data, String exportPath) throws IOException, Docx4JException {
        String filePath = "C:\\Users\\i.ahmadov\\Downloads\\";
        String file = "mezun_emri.docx";

        Docx4j docx4j = new Docx4j();
        WordprocessingMLPackage template = docx4j.getTemplate(resource.getFile().getPath());

        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<String, String>(){
            {
                this.put("${input_data}", data);
            }
            @Override
            public String get(Object key) {
                return super.get(key);
            }
        });

        docx4j.writeDocxToStream(template, filePath+"Hello2.docx");
        return new File(filePath+"Hello2.docx");
    }

    public static void searchAndReplace(List<Object> texts, Map<String, String> values){
        List<String> els = new ArrayList<String>(); 

        StringBuilder sb = new StringBuilder();
        int PASS = 0;
        int PREPARE = 1;
        int READ = 2;
        int mode = PASS;

        List<int[]> toNullify = new ArrayList<int[]>();
        int[] currentNullifyProps = new int[4];

        for(int i = 0; i<texts.size(); i++){
            Object text = texts.get(i);
            Text textElement = (Text) text;
            String newVal = "";
            String v = textElement.getValue();
            StringBuilder textSofar = new StringBuilder();
            int extra = 0;
            char[] vchars = v.toCharArray();
            for(int col = 0; col<vchars.length; col++){
                char c = vchars[col];
                textSofar.append(c);
                switch(c){
                case '$': {
                    mode=PREPARE;
                    sb.append(c);
                } break;
                case '{': {
                    if(mode==PREPARE){
                        sb.append(c);
                        mode=READ;
                        currentNullifyProps[0]=i;
                        currentNullifyProps[1]=col+extra-1;
                        System.out.println("extra-- "+extra);
                    } else {
                        if(mode==READ){
                            sb = new StringBuilder();
                            mode=PASS;
                        }
                    }
                } break;
                case '}': {
                    if(mode==READ){
                        mode=PASS;
                        sb.append(c);
                        els.add(sb.toString());
                        newVal +=textSofar.toString()
                                +(null==values.get(sb.toString())?sb.toString():values.get(sb.toString()));
                        textSofar = new StringBuilder();
                        currentNullifyProps[2]=i;
                        currentNullifyProps[3]=col+extra;
                        toNullify.add(currentNullifyProps);
                        currentNullifyProps = new int[4];
                        extra += sb.toString().length();
                        sb = new StringBuilder();
                    } else if(mode==PREPARE){
                        mode = PASS;
                        sb = new StringBuilder();
                    }
                }
                default: {
                    if(mode==READ) sb.append(c);
                    else if(mode==PREPARE){
                        mode=PASS;
                        sb = new StringBuilder();
                    }
                }
                }
            }
            newVal +=textSofar.toString();
            textElement.setValue(newVal);
        }

        if(toNullify.size()>0)
        for(int i = 0; i<texts.size(); i++){
            if(toNullify.size()==0) break;
            currentNullifyProps = toNullify.get(0);
            Object text = texts.get(i);
            Text textElement = (Text) text;
            String v = textElement.getValue();
            StringBuilder nvalSB = new StringBuilder();
            char[] textChars = v.toCharArray();
            for(int j = 0; j<textChars.length; j++){
                char c = textChars[j];
                if(null==currentNullifyProps) {
                    nvalSB.append(c);
                    continue;
                }
                int floor = currentNullifyProps[0]*100000+currentNullifyProps[1];
                int ceil = currentNullifyProps[2]*100000+currentNullifyProps[3];
                int head = i*100000+j;
                if(!(head>=floor && head<=ceil)){
                    nvalSB.append(c);
                }
                if(j>currentNullifyProps[3] && i>=currentNullifyProps[2]){
                    toNullify.remove(0);
                    if(toNullify.size()==0) {
                        currentNullifyProps = null;
                        continue;
                    }
                    currentNullifyProps = toNullify.get(0);
                }
            }
            textElement.setValue(nvalSB.toString());
        }
    }

    private WordprocessingMLPackage getTemplate(String name)
            throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage
                .load(new FileInputStream(new File(name)));
        return template;
    }

    private static List<Object> getAllElementFromObject(Object obj,
            Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

    private void writeDocxToStream(WordprocessingMLPackage template,
            String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }
}