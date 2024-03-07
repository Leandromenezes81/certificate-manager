package br.com.hgisystem.certificatemanager.core.exception;

import br.com.hgisystem.certificatemanager.core.message.DigitalCertificateInfoMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DigitalCertificateLoadException extends Exception{
    public DigitalCertificateLoadException() {
        super(DigitalCertificateInfoMessage.ERROR_LOAD_CERTIFICATE);
    }
}
