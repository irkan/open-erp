package com.openerp.task;

import com.openerp.domain.Schedule;
import com.openerp.entity.*;
import com.openerp.repository.*;
import com.openerp.service.SalesService;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PaymentLatencyTask {
    private static final Logger log = Logger.getLogger(PaymentLatencyTask.class);

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    PaymentLatencyRepository paymentLatencyRepository;

    @Autowired
    SalesService salesService;

    @Scheduled(cron="0 0 1 1 1/1 *") // her ayin 1 i saat 1 de iwliyecek
    public void saled() {
        try{
            log.info("Payment Latency Task Start");
            Date today = new Date();

            PaymentLatency paymentLatency;
            for(Organization organization: organizationRepository.getOrganizationsByActiveTrue()){
                try{
                    Sales salesObject = new Sales(null, organization);
                    salesObject.setService(null);
                    salesObject.setApprove(false);
                    salesObject.setSaled(false);
                    salesObject.setReturned(false);
                    Payment paymentObject = new Payment();
                    salesObject.setPayment(paymentObject);
                    salesObject.setSaleDate(null);

                    Page<Sales> sales = salesService.findAll(salesObject, PageRequest.of(0, 100000, Sort.by("id").descending()));
                    List<PaymentLatency> paymentLatencies = new ArrayList<>();
                    for(Sales sale: sales.getContent()){
                        try{
                            paymentLatency = new PaymentLatency(organization, today, sale.getId());
                            double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                            List<Schedule> schedules = new ArrayList<>();
                            if(sale.getPayment()!=null && !sale.getPayment().getCash()){
                                schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule(), sale.getPayment().getPeriod(), sale.getPayment().getLastPrice(), sale.getPayment().getDown(), Util.parseInt(sale.getPayment().getGracePeriod()));
                            }
                            double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                            if(sale.getPayment().getLastPrice()>sumOfInvoices && sumOfInvoices<plannedPayment){
                                schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                                int latency = Util.calculateLatency(schedules, sumOfInvoices, sale);
                                sale.getPayment().setLatency(latency);
                                sale.getPayment().setSumOfInvoice(sumOfInvoices);
                                sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                                sale.getPayment().setLastPaid(Util.getLastPaid(sale.getInvoices()));
                                if(latency>0){
                                    paymentLatency.setLatencyDay(latency);
                                    paymentLatency.setLatencySum(sale.getPayment().getUnpaid());
                                    paymentLatencies.add(paymentLatency);
                                }
                            }
                        } catch (Exception e){
                            log.error(e.getMessage(), e);
                        }
                    }
                    if(paymentLatencies.size()>0){
                        paymentLatencyRepository.saveAll(paymentLatencies);
                        log.info("Payment Latencies SAVED. branch: " + organization.getName() + "  |  size: " + paymentLatencies.size());
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }

            log.info("Payment Latency Task End");
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
