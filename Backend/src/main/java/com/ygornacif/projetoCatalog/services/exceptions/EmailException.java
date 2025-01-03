package com.ygornacif.projetoCatalog.services.exceptions;

import org.springframework.mail.MailException;

public class EmailException extends RuntimeException {

    public EmailException(String msg, MailException e) {
        super(msg);
    }
}
