package br.com.hgisystem.certificatemanager.core.exception;

import br.com.hgisystem.certificatemanager.core.message.DigitalCertificateInfoMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DigitalCertificateValidateException extends Exception {
    public DigitalCertificateValidateException() {
        super(DigitalCertificateInfoMessage.ERROR_INVALID_CERTIFICATE);
    }
}
