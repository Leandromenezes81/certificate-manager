package br.com.hgisystem.certificatemanager.controller.rest;

import br.com.hgisystem.certificatemanager.core.exception.*;
import br.com.hgisystem.certificatemanager.core.helpers.CertificateTransformBase64OrUrlToBytesHelper;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;
import br.com.hgisystem.certificatemanager.core.model.PaginationBase;
import br.com.hgisystem.certificatemanager.core.usecases.digitalcertificate.*;
import br.com.hgisystem.certificatemanager.dto.DigitalCertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/public/certificate/v1")
public class CertificateManagerController {
    @Inject
    private FindAllCertificateUseCase findAllCertificateUseCase;
    @Inject
    private GetCertificateByFederalTaxNumberUseCase getCertificateByFederalTaxNumberUseCase;
    @Inject
    private GetCertificateListByFederalTaxNumberUseCase getCertificateListByFederalTaxNumberUseCase;
    @Inject
    private ReadCertificateInfoUseCase readCertificateInfoUseCase;
    @Inject
    private AddCertificateUseCase addCertificateUseCase;
    @Inject
    private DownloadCertificateUseCase downloadCertificateUseCase;
    @Inject
    private CertificateTransformBase64OrUrlToBytesHelper certificateTransformBase64OrUrlToBytesHelper;

    @GetMapping("/findAll/page/{page}/pageSize/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationBase<Object> findAllCertificates(
            @PathVariable int page,
            @PathVariable int pageSize) {
        return this.findAllCertificateUseCase.findAll(page, pageSize);
    }

    @GetMapping("/info/{federalTaxNumber}")
    @ResponseStatus(HttpStatus.OK)
    public DigitalCertificate getCertificateByFederalTaxNumber(
            @PathVariable
            @Size(min = 11, max = 18) String federalTaxNumber) throws
            FederalTaxNumberNotFoundException,
            FederalTaxNumberInvalidCharactersException {
        return this.getCertificateByFederalTaxNumberUseCase
                .getCertificateByFederalTaxNumber(federalTaxNumber);
    }

    @GetMapping("/info/list/{federalTaxNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalCertificate> getCertificateListByFederalTaxNumber(
            @PathVariable
            @Size(min = 11, max = 18) String federalTaxNumber) throws
            FederalTaxNumberNotFoundException,
            FederalTaxNumberInvalidCharactersException {
        return this.getCertificateListByFederalTaxNumberUseCase
                .getCertificateListByFederalTaxNumber(federalTaxNumber);
    }

    @PostMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public DigitalCertificate readCertificateInfo(
            @Valid
            @RequestBody DigitalCertificateDTO body) throws
            IOException,
            DigitalCertificateLoadException {
        byte[] bytes = certificateTransformBase64OrUrlToBytesHelper
                .certificateTransformBase64OrUrlToBytes(body.getCertificateData());
        return this.readCertificateInfoUseCase.getCertificateInfo(
                bytes,
                body.getPassword());
    }

    @PostMapping("/add-certificate")
    @ResponseStatus(HttpStatus.CREATED)
    public DigitalCertificate addCertificate(
            @Valid
            @RequestBody DigitalCertificateDTO body) throws
            IOException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            NoSuchPaddingException,
            BadPaddingException,
            InvalidKeyException,
            FederalTaxNumberAlreadyExistsException,
            DigitalCertificateLoadException, DigitalCertificateValidateException,
            InvalidAlgorithmParameterException {
        byte[] bytes = certificateTransformBase64OrUrlToBytesHelper
                .certificateTransformBase64OrUrlToBytes(body.getCertificateData());
        return this.addCertificateUseCase.addCertificate(
                bytes,
                body.getPassword());
    }

    @GetMapping("/download/{federalTaxNumber}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] downloadCertificate(
            @PathVariable
            @Size(min = 11, max = 18) String federalTaxNumber) throws
            FederalTaxNumberInvalidCharactersException,
            InvalidAlgorithmParameterException,
            FederalTaxNumberNotFoundException,
            NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IOException,
            InvalidKeyException {
        return this.downloadCertificateUseCase.downloadCertificate(federalTaxNumber);
    }
}
