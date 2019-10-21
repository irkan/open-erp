package com.openerp.dummy;

import com.openerp.entity.Contact;
import com.openerp.entity.Dictionary;
import com.openerp.entity.Person;
import com.openerp.repository.DictionaryRepository;
import com.openerp.repository.PersonRepository;
import com.openerp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class DummyPerson {

    private List<String> firstNames = new ArrayList<String>();
    private List<String> lastNames = new ArrayList<String>();
    private List<String> fatherNames = new ArrayList<String>();

    public List<String> getFirstNames() {
        firstNames.add("Mahmud");
        firstNames.add("Əsli");
        firstNames.add("Əflatun");
        firstNames.add("İrkan");
        firstNames.add("İmdad");
        firstNames.add("Solmaz");
        firstNames.add("Lena");
        firstNames.add("Pünhan");
        firstNames.add("Lamiyə");
        firstNames.add("İsminaz");
        firstNames.add("Günay");
        firstNames.add("Vüqar");
        firstNames.add("Davud");
        firstNames.add("Məmməd");
        firstNames.add("Ülviyyə");
        firstNames.add("Jalə");
        firstNames.add("Səbinə");
        firstNames.add("Əminə");
        firstNames.add("Fatma");
        firstNames.add("Paşa");
        firstNames.add("Əlyar");
        firstNames.add("İlham");
        firstNames.add("Zöhrə");
        firstNames.add("Asif");
        firstNames.add("Nəsib");
        firstNames.add("Ramiz");
        firstNames.add("Pəri");
        firstNames.add("Ceyhun");
        firstNames.add("Mədinə");
        firstNames.add("Camal");
        firstNames.add("Allahverdi");
        firstNames.add("Gülarə");
        firstNames.add("Rüfət");
        firstNames.add("İnal");
        return firstNames;
    }

    public List<String> getLastNames() {
        lastNames.add("Mahmudov");
        lastNames.add("Əhmədov");
        lastNames.add("Davudov");
        lastNames.add("Məmmədov");
        lastNames.add("Paşayev");
        lastNames.add("Əlyarlı");
        lastNames.add("İsgəndərov");
        lastNames.add("Məmmədyarov");
        lastNames.add("Qarayev");
        lastNames.add("Abdullayev");
        lastNames.add("Mərdanov");
        lastNames.add("Camallı");
        lastNames.add("Həsənov");
        lastNames.add("Aslanlı");
        lastNames.add("Qaralı");
        return lastNames;
    }

    public List<String> getFatherNames() {
        fatherNames.add("Mahmud");
        fatherNames.add("Əflatun");
        fatherNames.add("İrkan");
        fatherNames.add("İmdad");
        fatherNames.add("Vüqar");
        fatherNames.add("Davud");
        fatherNames.add("Məmməd");
        fatherNames.add("Paşa");
        fatherNames.add("Əlyar");
        fatherNames.add("İlham");
        fatherNames.add("Asif");
        fatherNames.add("Nəsib");
        fatherNames.add("Ramiz");
        fatherNames.add("Ceyhun");
        fatherNames.add("Camal");
        fatherNames.add("Allahverdi");
        fatherNames.add("Rüfət");
        fatherNames.add("İnal");
        return fatherNames;
    }

    public Person getPerson(Contact contact, List<Dictionary> nationalities, List<Dictionary> genders, List<Dictionary> maritalStatuses){
        Person person = new Person(
                contact,
                DummyUtil.randomString(getFirstNames()),
                DummyUtil.randomString(getLastNames()),
                DummyUtil.randomString(getFatherNames()),
                new Date(),
                DummyUtil.randomDictionary(genders),
                DummyUtil.randomDictionary(nationalities),
                DummyUtil.randomDictionary(maritalStatuses),
                null,
                null
        );
        return person;
    }
}
