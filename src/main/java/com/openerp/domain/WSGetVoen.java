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
public class WSGetVoen {
    private String branch;
    @JsonProperty("voen-code")
    private Integer voenCode;
    private String voen;

    public WSGetVoen(String branch, Integer voenCode, String voen) {
        this.branch = branch;
        this.voenCode = voenCode;
        this.voen = voen;
    }
}
