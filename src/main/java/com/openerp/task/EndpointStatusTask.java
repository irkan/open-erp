package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.entity.*;
import com.openerp.repository.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
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
                    } else if (endpoint.getConnectionType().getAttr1().equalsIgnoreCase("telnet")){
                        TelnetClient telnet = new TelnetClient();
                        try{
                            telnet.connect(endpoint.getHost(),endpoint.getPort());
                            if(telnet.isConnected()!=endpoint.getStatus()){
                                endpoint.setLastStatusDate(new Date());
                                endpoint.setStatus(telnet.isConnected());
                                endpointRepository.save(endpoint);
                                description = (endpoint.getHost()!=null?endpoint.getHost():"") +
                                        (endpoint.getPort()!=null?(":"+endpoint.getPort()):"") + " " +
                                        (telnet.isConnected()?"- AKTİVLƏŞDİRİLDİ":"- SÖNDÜ") + " " +
                                        DateUtility.getFormattedDateTime(endpoint.getLastStatusDate());
                                endpointDetailRepository.save(new EndpointDetail(endpoint, endpoint.getLastStatusDate(), telnet.isConnected(), description));

                                if(endpoint.getEmail()!=null && endpoint.getEmail().trim().length()>3){
                                    skeletonController.sendEmail(null, endpoint.getEmail(), description, "", "");
                                }
                            }
                        } catch (Exception e){
                            log.error(e.getMessage(), e);
                        } finally {
                            telnet.disconnect();
                        }

                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
