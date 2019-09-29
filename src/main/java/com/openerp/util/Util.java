package com.openerp.util;

import com.openerp.entity.Action;
import com.openerp.entity.CurrencyRate;
import com.openerp.entity.ModuleOperation;
import com.openerp.entity.Organization;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class Util {
    public static Object check(Object object){
        try{
            if(object!=null)
                return object;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Organization findWarehouse(List<Organization> organizations){
        for(Organization organization: organizations){
            if(organization.getOrganizationType().getAttr1().equalsIgnoreCase("warehouse")){
                return organization;
            }
        }
        return null;
    }

    public static Organization getUserBranch(Organization organization){
        if(organization.getOrganizationType().getAttr1().equalsIgnoreCase("branch")){
            return organization;
        } else if(organization.getOrganization().getOrganizationType().getAttr1().equalsIgnoreCase("branch")){
            return organization.getOrganization();
        } else if(organization.getOrganization().getOrganization().getOrganizationType().getAttr1().equalsIgnoreCase("branch")){
            return organization.getOrganization().getOrganization();
        }
        return null;
    }

    public static int calculateInventoryAmount(List<Action> actions){
        int amount = 0;
        for(Action action: actions){
            amount+=action.getAmount();
        }
        return amount;
    }

    public static <Operation> ArrayList<Operation> removeDuplicateOperations(List<ModuleOperation> list) {
        ArrayList<Operation> newList = new ArrayList<>();
        for (ModuleOperation element : list) {
            if (!newList.contains(element.getOperation()) && element.getOperation()!=null) {
                newList.add((Operation) element.getOperation());
            }
        }
        return newList;
    }

    public static <Module> ArrayList<Module> removeDuplicateModules(List<ModuleOperation> list) {
        ArrayList<Module> newList = new ArrayList<>();
        for (ModuleOperation element : list) {
            if (!newList.contains(element.getModule())) {
                newList.add((Module) element.getModule());
            }
        }
        return newList;
    }

    public static List<CurrencyRate> getCurrenciesRate(String cbarCurrenciesEndpoint){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String endpoint = cbarCurrenciesEndpoint.replace("{dd.mm.yyyy}", DateUtility.getFormattedDate(new Date()));
        String value = restTemplate.getForObject(endpoint, String.class);

        Document doc = StringToXML.convertStringToXMLDocument( value );
        NodeList nList = doc.getElementsByTagName("ValCurs");
        List<CurrencyRate> currencyRates = new ArrayList<>();
        for(int i=0; i<nList.getLength(); i++){
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                NodeList valTypeNodeList = eElement.getElementsByTagName("ValType");
                for(int j=0; j<valTypeNodeList.getLength(); j++){
                    Node valTypeNode = valTypeNodeList.item(j);
                    if (valTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element valTypeElement = (Element) valTypeNode;
                        NodeList valuteNodeList = valTypeElement.getElementsByTagName("Valute");
                        for(int k=0; k<valuteNodeList.getLength(); k++){
                            Node valuteNode = valuteNodeList.item(k);
                            Element valuteElement = (Element) valuteNode;
                            CurrencyRate currencyRate = new CurrencyRate();
                            currencyRate.setRateDate(DateUtility.getUtilDate(eElement.getAttribute("Date")));
                            currencyRate.setType(valTypeElement.getAttribute("Type"));
                            currencyRate.setCode(valuteElement.getAttribute("Code"));
                            currencyRate.setNominal(valuteElement.getElementsByTagName("Nominal").item(0).getTextContent());
                            currencyRate.setName(valuteElement.getElementsByTagName("Name").item(0).getTextContent());
                            currencyRate.setValue(Double.parseDouble(valuteElement.getElementsByTagName("Value").item(0).getTextContent()));
                            currencyRates.add(currencyRate);
                        }
                    }
                }
            }
        }
        return currencyRates;
    }
}
