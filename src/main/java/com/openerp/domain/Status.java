package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Status {
    private int moduleOperationId;
    private boolean checked;

    public Status(int moduleOperationId, boolean checked) {
        this.moduleOperationId = moduleOperationId;
        this.checked = checked;
    }
}
