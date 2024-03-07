package br.com.hgisystem.certificatemanager.core.usecases.digitalcertificate;

import br.com.hgisystem.certificatemanager.core.dataprovider.repository.CertificateRepository;
import br.com.hgisystem.certificatemanager.core.exception.DigitalCertificateLoadException;
import br.com.hgisystem.certificatemanager.core.exception.DigitalCertificateValidateException;
import br.com.hgisystem.certificatemanager.core.exception.FederalTaxNumberAlreadyExistsException;
import br.com.hgisystem.certificatemanager.core.helpers.CertificateManagerHelper;
import br.com.hgisystem.certificatemanager.core.helpers.CryptoHelper;
import br.com.hgisystem.certificatemanager.core.helpers.ObjectConverterHelper;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;
import br.com.hgisystem.certificatemanager.core.model.FederalTaxInfo;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

@Named
@Transactional(rollbackOn = Throwable.class)
public class AddCertificateUseCase {
    @Inject
    private CertificateRepository certificateRepository;
    @Inject
    private CertificateManagerHelper certificateManagerHelper;
    @Inject
    private ObjectConverterHelper objectConverterHelper;
    @Inject
    private CryptoHelper cryptoHelper;

    public DigitalCertificate addCertificate(byte[] bytes, String password) throws
            FederalTaxNumberAlreadyExistsException,
            DigitalCertificateLoadException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, DigitalCertificateValidateException, InvalidAlgorithmParameterException {
        X509Certificate x509Certificate;

        try {
            x509Certificate = certificateManagerHelper.transformBytesToCertificate(bytes, password);
        } catch (Exception e) {
            throw new DigitalCertificateLoadException();
        }

        try {
            x509Certificate.checkValidity();
        } catch (CertificateExpiredException | CertificateNotYetValidException e){
            throw new DigitalCertificateValidateException();
        }

        FederalTaxInfo federalTaxInfo = certificateManagerHelper.getFederalTaxInfo(x509Certificate);

        DigitalCertificate alreadyExists = certificateRepository.findByFederalTaxNumberCreatedAt(federalTaxInfo.getFederalTaxNumber(), x509Certificate.getNotBefore());

        if (alreadyExists != null) {
            throw new FederalTaxNumberAlreadyExistsException();
        }

        DigitalCertificate digitalCertificate = fillCertificateInfo(x509Certificate, federalTaxInfo, bytes, password);

        return this.certificateRepository.save(digitalCertificate);
    }

    public DigitalCertificate fillCertificateInfo(X509Certificate x509Certificate, FederalTaxInfo federalTaxInfo, byte[] bytes, String password)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String base64 = objectConverterHelper.byteToBase64(bytes);

        DigitalCertificate digitalCertificate = new DigitalCertificate();
        digitalCertificate.setCompanyName(certificateManagerHelper.getCompanyName(x509Certificate));
        digitalCertificate.setFederalTaxNumber(federalTaxInfo.getFederalTaxNumber());
        digitalCertificate.setCompanyType(federalTaxInfo.getCompanyType());
        digitalCertificate.setIssuer(certificateManagerHelper.getIssuer(x509Certificate));
        digitalCertificate.setCertificateData(cryptoHelper.encryptValue(base64));
        digitalCertificate.setPassword(cryptoHelper.encryptValue(password));
        digitalCertificate.setCreatedAt(x509Certificate.getNotBefore());
        digitalCertificate.setValidUntil(x509Certificate.getNotAfter());

        return digitalCertificate;
    }
}
