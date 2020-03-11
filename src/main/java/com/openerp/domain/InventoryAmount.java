package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryAmount {
    private int allItemsCount;
    private int oldItemsCount;

    public InventoryAmount(int allItemsCount, int oldItemsCount) {
        this.allItemsCount = allItemsCount;
        this.oldItemsCount = oldItemsCount;
    }
}
