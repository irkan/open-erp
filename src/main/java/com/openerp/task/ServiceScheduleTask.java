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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ServiceScheduleTask {
    private static final Logger log = Logger.getLogger(ServiceScheduleTask.class);

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    ServiceRegulatorRepository serviceRegulatorRepository;

    @Autowired
    ServiceTaskRepository serviceTaskRepository;

    @Autowired
    ServiceRegulatorTaskRepository serviceRegulatorTaskRepository;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Autowired
    SkeletonController skeletonController;

    @Scheduled(/*cron = "0 0 0 * * *", */fixedDelay = 60000)
    public void service() {
        try{
            log.info("Service Regulator Task Start");
            serviceTaskRepository.deleteAllInBatch();
            serviceRegulatorTaskRepository.deleteAllInBatch();
            Configuration configuration = configurationRepository.getConfigurationByKey("service");
            String defaultValue = configuration!=null?configuration.getAttribute():"6";
            Date today = new Date();
            for(Sales sales: salesRepository.getSalesByActiveTrueAndServiceFalseAndApproveTrueOrderByIdAsc()){
                try {
                    ServiceTask serviceTask = new ServiceTask();
                    String description = "";
                    List<ServiceRegulatorTask> serviceRegulatorTasks = new ArrayList<>();
                    for(ServiceRegulator serviceRegulator: sales.getServiceRegulators()){
                        Date servicedDate = serviceRegulator.getServicedDate();
                        Date serviceDate = DateUtility.addMonth(servicedDate.getDate(), servicedDate.getMonth(), servicedDate.getYear()+1900, Util.parseInt(serviceRegulator.getServiceNotification().getAttr2(), defaultValue));
                        if(serviceDate.getTime()<=today.getTime() && !sales.getNotServiceNext() && Util.calculateInvoice(sales.getInvoices())>0){
                            serviceRegulatorTasks.add(new ServiceRegulatorTask(serviceTask, serviceRegulator));
                            description += serviceRegulator.getServiceNotification().getName() + " ";
                        }
                    }
                    if(serviceRegulatorTasks.size()>0){
                        description += " filterləri dəyişməlidir";
                        serviceTask.setDescription(description);
                        serviceTask.setOrganization(sales.getOrganization());
                        serviceTask.setSales(sales);
                        serviceTask.setServiceRegulatorTasks(serviceRegulatorTasks);
                        serviceTask.setTaskDate(new Date());
                        serviceTaskRepository.save(serviceTask);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    log.error(e);
                }
            }
            log.info("Service Regulator Task End");
        } catch (Exception e){
            log.error(e);
        }
    }
}
