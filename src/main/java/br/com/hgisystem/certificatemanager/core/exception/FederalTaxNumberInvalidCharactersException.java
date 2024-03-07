package br.com.hgisystem.certificatemanager.core.exception;

import br.com.hgisystem.certificatemanager.core.message.FederalTaxNumberMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FederalTaxNumberInvalidCharactersException extends Exception{
    public FederalTaxNumberInvalidCharactersException() {
        super(FederalTaxNumberMessage.EXCEPTION_FEDERAL_TAX_NUMBER_INVALID_CHARACTERS);
    }
}
