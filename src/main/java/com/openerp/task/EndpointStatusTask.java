package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.entity.*;
import com.openerp.repository.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.net.telnet.TelnetClient;

@Component
public class EndpointStatusTask {
    private static final Logger log = Logger.getLogger(EndpointStatusTask.class);

    @Autowired
    EndpointRepository endpointRepository;

    @Autowired
    EndpointDetailRepository endpointDetailRepository;

    @Autowired
    SkeletonController skeletonController;

    @Scheduled(fixedDelay = 10000, initialDelay = 30000)
    public void service() {
        try{
            TelnetClient telnet = new TelnetClient();
            boolean telnetStatus = false;
            try{
                telnet.connect("google.com",443);
                telnetStatus = telnet.isConnected();
                telnet.disconnect();
            } catch (Exception e){}

            if(telnetStatus){
                for(Endpoint endpoint: endpointRepository.getEndpointsByActiveTrue()){
                    try{
                        String type="info";
                        String description = "";
                        if (endpoint.getConnectionType().getAttr1().equalsIgnoreCase("ping")){
                            InetAddress address = InetAddress.getByName(endpoint.getHost());
                            if(address.isReachable(5000)){
                                endpoint.setLastStatusDate(new Date());
                                endpointRepository.save(endpoint);
                            } else {
                                type="error";
                                description = endpoint.getHost()+" ping is not reachable";
                            }
                        } else if (endpoint.getConnectionType().getAttr1().equalsIgnoreCase("http")){
                            try{
                                boolean httpStatus = false;
                                try{
                                    Document document = Jsoup.connect(endpoint.getUrl()).ignoreHttpErrors(true).get();
                                    Elements logins = document.body().getElementsByClass(endpoint.getCssClass());
                                    if(logins.size()>0){
                                        httpStatus = true;
                                    } else {
                                        httpStatus = false;
                                    }
                                } catch (Exception e){
                                    httpStatus = false;
                                }
                                if(httpStatus!=endpoint.getStatus()){
                                    endpoint.setLastStatusDate(new Date());
                                    endpoint.setStatus(httpStatus);
                                    endpointRepository.save(endpoint);

                                    description = endpoint.getUrl() + " " +
                                            (httpStatus?"- AKTİVLƏŞDİRİLDİ":"- SÖNDÜ") + " / " +
                                            DateUtility.getFormattedDateTimeSS(endpoint.getLastStatusDate());
                                    EndpointDetail endpointDetail=null;
                                    if(!httpStatus){
                                        endpointDetail = new EndpointDetail();
                                        endpointDetail.setEndpoint(endpoint);
                                        endpointDetail.setDescription(description);
                                        endpointDetail.setDownDate(endpoint.getLastStatusDate());
                                    } else {
                                        List<EndpointDetail> endpointDetails = endpointDetailRepository.getEndpointDetailsByDownDateIsNotNullAndUpDateIsNullAndActiveTrueOrderByDownDateDesc();
                                        if(endpointDetails.size()>0){
                                            endpointDetail = endpointDetails.get(0);
                                            endpointDetail.setUpDate(endpoint.getLastStatusDate());
                                            endpointDetail.setDescription(endpointDetail.getDescription()+";  " + description);
                                            endpointDetail.setDifferent(TimeUnit.SECONDS.convert(endpointDetail.getUpDate().getTime()-endpointDetail.getDownDate().getTime(), TimeUnit.SECONDS));
                                        }
                                    }

                                    if(endpointDetail!=null){
                                        endpointDetailRepository.save(endpointDetail);

                                        if(endpoint.getEmail()!=null && endpoint.getEmail().trim().length()>3){
                                            if(endpoint.getEmail().trim().length()>0){
                                                for(String email: endpoint.getEmail().trim().split(Pattern.quote(";"))){
                                                    if(email!=null && email.trim().length()>0 && email.matches(Constants.REGEX.REGEX4)){
                                                        skeletonController.sendEmail(null, email, description, description, description);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e){
                                log.error(e.getMessage(), e);
                            }
                        } /*else if (endpoint.getConnectionType().getAttr1().equalsIgnoreCase("telnet")){
                            try{
                                telnetStatus = false;
                                try{
                                    telnet.connect(endpoint.getHost(),endpoint.getPort());
                                    telnetStatus = telnet.isConnected();
                                    telnet.disconnect();
                                } catch (Exception e){}
                                if(telnetStatus!=endpoint.getStatus()) {
                                    for (int i = 0; i < 3; i++) {
                                        try{
                                            telnet.connect(endpoint.getHost(),endpoint.getPort());
                                            telnetStatus = telnet.isConnected();
                                            telnet.disconnect();
                                        } catch (Exception e){}
                                    }
                                }
                                if(telnetStatus!=endpoint.getStatus()){
                                    description = (endpoint.getHost()!=null?endpoint.getHost():"") +
                                            (endpoint.getPort()!=null?(":"+endpoint.getPort()):"") + " " +
                                            (telnetStatus?"- AKTİVLƏŞDİRİLDİ":"- SÖNDÜ") + " / " +
                                            DateUtility.getFormattedDateTimeSS(endpoint.getLastStatusDate());
                                    endpointDetailRepository.save(new EndpointDetail(endpoint, description));

                                    if(endpoint.getEmail()!=null && endpoint.getEmail().trim().length()>3){
                                        skeletonController.sendEmail(null, endpoint.getEmail(), description, description, description);
                                    }
                                }
                            } catch (Exception e){
                                log.error(e.getMessage(), e);
                            } finally {
                                telnet.disconnect();
                            }

                        }*/
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
