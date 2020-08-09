package com.openerp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openerp.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WSInfo {
    @JsonProperty("sale-code")
    private Integer salesCode;
    private String customer;
    @JsonProperty("contract-amount")
    private Double contractAmount;
    private Double paid;
    private Double balance;
    private Double unpaid;
    private String sale;
    private String payment;
    @JsonProperty("voen-code")
    private Integer voenCode;
    private String voen;
    private String label;

    public WSInfo(Integer salesCode, String customer, Double contractAmount, Double paid, Double unpaid, String sale, String payment, Integer voenCode, String voen) {
        this.salesCode = salesCode;
        this.customer = customer;
        this.contractAmount = contractAmount;
        this.balance = contractAmount-paid;
        this.paid = paid;
        this.unpaid = unpaid;
        this.sale = sale;
        this.payment = payment;
        this.voenCode = voenCode;
        this.voen = voen;
        String label = "";
        try {
            label = this.unpaid>0?("Cari borc: " + Util.format3(this.unpaid) + "₼"):"";
            label += this.balance>0?(", Ümumi qalıq: " + Util.format3(this.balance) + "₼"):"";
            label += this.paid>0?(", Ödənilib: " + Util.format3(this.paid) + "₼"):"";
            label += this.contractAmount>0?(", Müqavilə məbləği: " + Util.format3(this.contractAmount) + "₼"):"";
        } catch (Exception e){
            e.printStackTrace();
        }
        this.label = label;
    }
}
