package br.com.hgisystem.certificatemanager.dto;

import br.com.hgisystem.certificatemanager.core.message.DigitalCertificateInfoMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class DigitalCertificateDTO {
    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_CERTIFICATE_DATA)
    private String certificateData;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_PASSWORD)
    private String password;
}
