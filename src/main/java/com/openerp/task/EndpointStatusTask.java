package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.entity.*;
import com.openerp.repository.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EndpointStatusTask {
    private static final Logger log = Logger.getLogger(EndpointStatusTask.class);

    @Autowired
    EndpointRepository endpointRepository;

    @Autowired
    SkeletonController skeletonController;

    @Scheduled(fixedDelay = 300000)
    public void service() {
        try{
            log.info("Endpoint Status Task Start");

            for(Endpoint endpoint: endpointRepository.findAll()){
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
                    InputStream input = null;
                    InputStreamReader reader = null;
                    try (Socket socket = new Socket(endpoint.getHost(), endpoint.getPort())) {
                        input = socket.getInputStream();
                        reader = new InputStreamReader(input);
                        int character;
                        StringBuilder data = new StringBuilder();
                        while ((character = reader.read()) != -1) {
                            data.append((char) character);
                        }
                        if(data.length()>0){
                            endpoint.setLastStatusDate(new Date());
                            endpointRepository.save(endpoint);
                        }
                        System.out.println(data);
                    } catch (UnknownHostException e){
                        skeletonController.log(null, "error", "", "", null, "", e.getMessage());
                        e.printStackTrace();
                        log.error("Server not found: " + e.getMessage());
                        type="error";
                        description = "Telnet "+ endpoint.getHost() + ":" + endpoint.getPort() +" server not found: " + e.getMessage();
                    } catch (IOException e){
                        skeletonController.log(null, "error", "", "", null, "", e.getMessage());
                        e.printStackTrace();
                        log.error("I/O error: " + e.getMessage());
                        type="error";
                        description = "Telnet "+ endpoint.getHost() + ":" + endpoint.getPort() +" I/O error: " + e.getMessage();
                    } finally{
                        reader.close();
                        input.close();
                    }
                }
                skeletonController.log(type, "endpoint", "create/edit", endpoint.getId(), endpoint.toString(), description);
            }

            log.info("Endpoint Status Task End");
        } catch (Exception e){
            skeletonController.log(null, "error", "", "", null, "", e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
