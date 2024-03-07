package br.com.hgisystem.certificatemanager.core.helpers;

import br.com.hgisystem.certificatemanager.core.model.FederalTaxInfo;
import br.com.hgisystem.certificatemanager.core.model.enumerator.CompanyType;

import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;

@Named
public class CertificateManagerHelper {
    public X509Certificate transformBytesToCertificate(byte[] bytes, String password) throws
            KeyStoreException,
            CertificateException,
            IOException,
            NoSuchAlgorithmException {
        X509Certificate x509Certificate = null;
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new ByteArrayInputStream(bytes), password.toCharArray());

        String alias = keyStore.aliases().nextElement();
        Certificate certificate = keyStore.getCertificate(alias);

        if (certificate instanceof X509Certificate)
            x509Certificate = (X509Certificate) certificate;

        return x509Certificate;
    }

    public String getCompanyName(X509Certificate x509Certificate) {
        String subject = x509Certificate.getSubjectX500Principal().getName();
        int startIdx = subject.indexOf("CN=") + 3;
        int endIdx = subject.indexOf(':');
        return subject.substring(startIdx, endIdx);
    }

    public String getIssuer(X509Certificate x509Certificate) {
        String issuer = x509Certificate.getIssuerX500Principal().toString();
        int startIdx = issuer.indexOf("CN=") + 3;
        int endIdx = issuer.indexOf(',');
        return issuer.substring(startIdx, endIdx);
    }

    public FederalTaxInfo getFederalTaxInfo(X509Certificate x509Certificate) {
        String subject = x509Certificate.getSubjectX500Principal().getName();
        int startIdx = subject.indexOf(':');
        int endIdx = subject.indexOf(',');
        String federalTaxNumber = subject
                .substring((startIdx + 1), endIdx)
                .trim();
        String companyType = "";

        switch (federalTaxNumber.length()) {
            case 14:
                companyType = CompanyType.PESSOA_JURIDICA.getValue();
                break;
            case 11:
                companyType = CompanyType.PESSOA_FISICA.getValue();
                break;
        }
        return new FederalTaxInfo(federalTaxNumber, companyType);
    }

    public boolean checkCertificateValidity(X509Certificate x509Certificate) {
        try {
            x509Certificate.checkValidity();
            return true;
        } catch (CertificateNotYetValidException |
                 CertificateExpiredException e) {
            return false;
        }
    }
}
