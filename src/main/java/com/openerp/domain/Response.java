package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    private String type;
    private List<String> messages;

    public Response(String type, List<String> messages) {
        this.type = type;
        this.messages = messages;
    }
}
