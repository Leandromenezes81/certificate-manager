package br.com.hgisystem.certificatemanager.core.model;

import br.com.hgisystem.certificatemanager.core.message.DigitalCertificateInfoMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "digital_certificate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
public class DigitalCertificate {
    @SequenceGenerator(name="seq_default", sequenceName="seq_default", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_default")
    @Id
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_COMPANY_NAME)
    @Column(name = "company_name")
    private String companyName;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_FEDERAL_TAX_NUMBER)
    @Column(name = "federal_tax_number")
    private String federalTaxNumber;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_COMPANY_TYPE)
    @Column(name = "company_type")
    private String companyType;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_ISSUER)
    @Column(name = "issuer")
    private String issuer;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_CERTIFICATE_DATA)
    @Column(name = "certificate_data")
    private String certificateData;

    @NotEmpty(message = DigitalCertificateInfoMessage.REQUIRED_PASSWORD)
    @Column(name = "password")
    private String password;

    @NotNull(message = DigitalCertificateInfoMessage.REQUIRED_CREATED_AT)
    @CreatedDate
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull(message = DigitalCertificateInfoMessage.REQUIRED_VALID_UNTIL)
    @Column(name = "valid_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;
}
