package com.trial.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "product not found")
public class ProductNotFoundException extends Exception {

    public ProductNotFoundException() {
        super("product not found");
    }
}
