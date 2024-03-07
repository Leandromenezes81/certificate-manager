package br.com.hgisystem.certificatemanager.core.exception;

import br.com.hgisystem.certificatemanager.core.message.FederalTaxNumberMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FederalTaxNumberAlreadyExistsException extends Exception{
    public FederalTaxNumberAlreadyExistsException() {
        super(FederalTaxNumberMessage.EXCEPTION_FEDERAL_TAX_NUMBER_ALREADY_EXISTS);
    }
}
