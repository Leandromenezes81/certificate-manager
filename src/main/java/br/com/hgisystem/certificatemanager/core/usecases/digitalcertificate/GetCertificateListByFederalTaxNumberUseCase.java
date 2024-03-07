package br.com.hgisystem.certificatemanager.core.usecases.digitalcertificate;

import br.com.hgisystem.certificatemanager.core.dataprovider.repository.CertificateRepository;
import br.com.hgisystem.certificatemanager.core.exception.FederalTaxNumberInvalidCharactersException;
import br.com.hgisystem.certificatemanager.core.exception.FederalTaxNumberNotFoundException;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@Transactional(rollbackOn = Throwable.class)
public class GetCertificateListByFederalTaxNumberUseCase {
    @Inject
    private CertificateRepository certificateRepository;

    public List<DigitalCertificate> getCertificateListByFederalTaxNumber(String federalTaxNumber) throws
            FederalTaxNumberInvalidCharactersException,
            FederalTaxNumberNotFoundException {

        if (!federalTaxNumber.matches("[0-9]+"))
            throw new FederalTaxNumberInvalidCharactersException();

        List<DigitalCertificate> resultList = this.certificateRepository.findByFederalTaxNumberOrderByValidUntil(federalTaxNumber);

        if (resultList.isEmpty()) {
            throw new FederalTaxNumberNotFoundException();
        }
        return resultList;
    }
}
