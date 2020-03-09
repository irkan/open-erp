package com.openerp.domain;

import com.openerp.entity.SalesInventory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Return {
    private List<SalesInventory> salesInventories;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    private Double returnPrice=0d;
    private Integer salesId;
    private String reason;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date=new Date();


}
