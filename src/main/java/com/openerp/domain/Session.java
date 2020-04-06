package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Session {
    private String id;
    private Date expire;
    private String username;
    private String fullName;
    private float iddle;

    public Session(String id, Date expire) {
        this.id = id;
        this.expire = expire;
    }
}
