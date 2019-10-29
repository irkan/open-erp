package com.openerp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.springframework.core.io.Resource;

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