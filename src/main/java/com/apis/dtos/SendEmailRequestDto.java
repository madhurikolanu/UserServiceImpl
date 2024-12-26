package com.apis.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailRequestDto {
    private String to;
    private String from;
    private String body;
    private String subject;
}
