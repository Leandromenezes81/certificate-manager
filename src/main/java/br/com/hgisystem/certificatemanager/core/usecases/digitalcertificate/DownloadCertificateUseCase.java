package br.com.hgisystem.certificatemanager.core.usecases.digitalcertificate;

import br.com.hgisystem.certificatemanager.core.exception.FederalTaxNumberInvalidCharactersException;
import br.com.hgisystem.certificatemanager.core.exception.FederalTaxNumberNotFoundException;
import br.com.hgisystem.certificatemanager.core.helpers.CryptoHelper;
import br.com.hgisystem.certificatemanager.core.helpers.ObjectConverterHelper;
import br.com.hgisystem.certificatemanager.core.helpers.ZipFileHelper;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipOutputStream;

@Named
@Transactional(rollbackOn = Throwable.class)
public class DownloadCertificateUseCase {
    @Inject
    private GetCertificateByFederalTaxNumberUseCase getCertificateByFederalTaxNumberUseCase;
    @Inject
    private CryptoHelper cryptoHelper;
    @Inject
    private ObjectConverterHelper objectConverterHelper;
    @Inject
    private ZipFileHelper zipFileHelper;
    public byte[] downloadCertificate(String federalTaxNumber) throws
            FederalTaxNumberInvalidCharactersException,
            FederalTaxNumberNotFoundException,
            InvalidAlgorithmParameterException,
            NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            BadPaddingException,
            InvalidKeyException,
            IOException {
        if (!federalTaxNumber.matches("[0-9]+")) {
            throw new FederalTaxNumberInvalidCharactersException();
        }

        DigitalCertificate certificate = getCertificateByFederalTaxNumberUseCase
                        .getCertificateByFederalTaxNumber(federalTaxNumber);

        String base64Certificate = cryptoHelper.decryptValue(certificate.getCertificateData());
        String passwordCertificate = cryptoHelper.decryptValue(certificate.getPassword());

        byte[] certificateBytes = objectConverterHelper.base64ToByte(base64Certificate);
        byte[] passwordBytes = passwordCertificate.getBytes();

        String certificateFileName = certificate.getCompanyName().replace(" ","_")
                +"_"+ certificate.getFederalTaxNumber();

        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipFile = new ZipOutputStream(zipOutputStream)) {
            zipFileHelper.addBytesToZip(
                    certificateBytes,
                    certificateFileName + ".pfx",
                    zipFile);
            zipFileHelper.addBytesToZip(
                    passwordBytes,
                    "password.txt",
                    zipFile);
        }
        return cryptoHelper.encryptValue(zipOutputStream.toByteArray());
    }
}
