package br.com.hgisystem.certificatemanager.core.dataprovider.repository;

import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;

import java.util.Date;
import java.util.List;


public interface CertificateRepository {
    List<DigitalCertificate> findAll(int firstResult, int pageSize);
    Long countAll();
    DigitalCertificate save (DigitalCertificate certificate);
    DigitalCertificate findByFederalTaxNumber(String federalTaxNumber);
    DigitalCertificate findByFederalTaxNumberCreatedAt(String federalTaxNumber, Date createdAt);
    List<DigitalCertificate> findByFederalTaxNumberOrderByValidUntil(String federalTaxNumber);
}
