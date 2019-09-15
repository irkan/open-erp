package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Status {
    private Object object;
    private boolean status;

    public Status(Object object, boolean status) {
        this.object = object;
        this.status = status;
    }
}
