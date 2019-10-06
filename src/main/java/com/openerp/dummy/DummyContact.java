package com.openerp.dummy;

import com.openerp.entity.Contact;
import com.openerp.entity.Dictionary;
import com.openerp.entity.Person;
import com.openerp.repository.ContactRepository;
import com.openerp.repository.DictionaryRepository;
import com.openerp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class DummyContact {

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    ContactRepository contactRepository;

    private List<String> mobilePhones = new ArrayList<String>();
    private List<String> homePhones = new ArrayList<String>();
    private List<String> emails = new ArrayList<String>();
    private List<String> addresses = new ArrayList<String>();

    public List<String> getMobilePhones() {
        mobilePhones.add("502535110");
        mobilePhones.add("502901390");
        mobilePhones.add("507252207");
        mobilePhones.add("556782399");
        mobilePhones.add("512883678");
        mobilePhones.add("708334588");
        mobilePhones.add("776569878");
        mobilePhones.add("515987398");
        mobilePhones.add("776592209");
        mobilePhones.add("703894456");
        return mobilePhones;
    }

    public List<String> getHomePhones() {
        homePhones.add("186526788");
        homePhones.add("128495858");
        homePhones.add("126669393");
        homePhones.add("127483392");
        homePhones.add("187382987");
        homePhones.add("126592227");
        return homePhones;
    }

    public List<String> getEmails() {
        emails.add("irkan.ehmedov@gmail.com");
        emails.add("irkan.ahmadov@pashabank.az");
        emails.add("irkan.ahmadov@azsigorta.az");
        emails.add("i.ahmadov@italdizain.az");
        emails.add("irkan_89@mail.ru");
        emails.add("irkan_89@hotmail.com");
        emails.add("imdad.ehmedzade@gmail.com");
        emails.add("pasha.aliyev@gmail.com");
        emails.add("elyar.mammadov@mail.ru");
        return emails;
    }

    public List<String> getAddresses() {
        addresses.add("Sulh kuc ev 8 m 9");
        addresses.add("20 Yanvar kuc ev 9");
        addresses.add("A.Aghayev kuc ev 67 m55");
        addresses.add("Dadashlinski kuc ev 36A");
        addresses.add("Dilbazi 67B");
        addresses.add("Semenderov 5 m 99");
        addresses.add("Azadliq pros ev 6/19 m 9");
        addresses.add("Parlament pros ev 66 m 4A");
        addresses.add("Rustavi 55B");
        return addresses;
    }

    public Contact getContact(List<Dictionary> cities){
        Contact contact = new Contact(
                DummyUtil.randomString(getMobilePhones()),
                DummyUtil.randomString(getHomePhones()),
                DummyUtil.randomString(getEmails()),
                DummyUtil.randomString(getAddresses()),
                DummyUtil.randomDictionary(cities)
        );
        return contact;
    }
}
