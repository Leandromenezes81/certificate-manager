package br.com.hgisystem.certificatemanager.core.usecases.digitalcertificate;

import br.com.hgisystem.certificatemanager.core.exception.DigitalCertificateLoadException;
import br.com.hgisystem.certificatemanager.core.helpers.CertificateManagerHelper;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;
import br.com.hgisystem.certificatemanager.core.model.FederalTaxInfo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.security.cert.X509Certificate;

@Named
@Transactional(rollbackOn = Throwable.class)
public class ReadCertificateInfoUseCase {
    @Inject
    private CertificateManagerHelper certificateManagerHelper;

    public DigitalCertificate getCertificateInfo(byte[] bytes, String password) throws
            DigitalCertificateLoadException
    {
        X509Certificate x509Certificate;

        try {
            x509Certificate = certificateManagerHelper.transformBytesToCertificate(bytes, password);
        } catch (Exception e) {
            throw new DigitalCertificateLoadException();
        }

        FederalTaxInfo federalTaxInfo = certificateManagerHelper.getFederalTaxInfo(x509Certificate);

        return fillCertificateInfo(x509Certificate, federalTaxInfo);
    }

    private DigitalCertificate fillCertificateInfo(X509Certificate x509Certificate, FederalTaxInfo federalTaxInfo){
        DigitalCertificate certificate = new DigitalCertificate();
        certificate.setCompanyName(certificateManagerHelper.getCompanyName(x509Certificate));
        certificate.setFederalTaxNumber(federalTaxInfo.getFederalTaxNumber());
        certificate.setCompanyType(federalTaxInfo.getCompanyType());
        certificate.setIssuer(certificateManagerHelper.getIssuer(x509Certificate));
        certificate.setCreatedAt(x509Certificate.getNotBefore());
        certificate.setValidUntil(x509Certificate.getNotAfter());

        return certificate;
    }
}
