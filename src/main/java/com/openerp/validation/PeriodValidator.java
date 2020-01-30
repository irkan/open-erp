package com.openerp.validation;

import com.openerp.config.FieldValueExists;
import com.openerp.entity.User;
import com.openerp.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodValidator implements FieldValueExists {
    @Autowired
    private PeriodRepository periodRepository;

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("user")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }
        User user = (User) value;
        if(periodRepository.getPeriodsByUser(user).size()>0){
            return true;
        }
        return false;
    }
}
