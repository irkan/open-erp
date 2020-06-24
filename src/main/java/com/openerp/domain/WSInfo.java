package com.openerp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Double paid;
    private Double unpaid;
    private String sale;
    private String payment;
    @JsonProperty("voen-code")
    private Integer voenCode;
    private String voen;

    public WSInfo(Integer salesCode, String customer, Double paid, Double unpaid, String sale, String payment, Integer voenCode, String voen) {
        this.salesCode = salesCode;
        this.customer = customer;
        this.paid = paid;
        this.unpaid = unpaid;
        this.sale = sale;
        this.payment = payment;
        this.voenCode = voenCode;
        this.voen = voen;
    }
}
