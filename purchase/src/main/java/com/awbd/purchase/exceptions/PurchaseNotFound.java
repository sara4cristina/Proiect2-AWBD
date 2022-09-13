package com.awbd.purchase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PurchaseNotFound extends RuntimeException {
    public PurchaseNotFound(String message) {
        super(message);
    }
}
