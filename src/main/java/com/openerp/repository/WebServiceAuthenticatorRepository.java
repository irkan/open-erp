package com.openerp.repository;

import com.openerp.entity.GlobalConfiguration;
import com.openerp.entity.WebServiceAuthenticator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebServiceAuthenticatorRepository extends JpaRepository<WebServiceAuthenticator, Integer> {
    WebServiceAuthenticator getWebServiceAuthenticatorByUsernameAndPasswordAndActiveTrue(String username, String password);
    WebServiceAuthenticator getWebServiceAuthenticatorById(Integer id);
}