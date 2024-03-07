package br.com.hgisystem.certificatemanager.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FilterNotImplementedException extends Exception {
    public FilterNotImplementedException(){
        super("Filter not implemented!");
    }
}
