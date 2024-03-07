package br.com.hgisystem.certificatemanager.repository.impl;

import br.com.hgisystem.certificatemanager.core.dataprovider.repository.CertificateRepository;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;
import br.com.hgisystem.certificatemanager.repository.filter.CertificateFilter;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
@Data
public class CertificateRepositoryImpl implements CertificateRepository {
    @Inject
    protected EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<DigitalCertificate> findAll(int firstResult, int pageSize) {
        CertificateFilter filter = new CertificateFilter(entityManager);
        return filter
                .createQuery()
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Long countAll() {
        CertificateFilter filter = new CertificateFilter(entityManager);
        return (Long) filter.createQueryCount().getSingleResult();
    }

    @Override
    public DigitalCertificate save(DigitalCertificate certificate) {
        return this.entityManager.merge(certificate);
    }

    @Override
    public DigitalCertificate findByFederalTaxNumber(String federalTaxNumber) {
        CertificateFilter filter = new CertificateFilter(entityManager);
        filter.filterByFederalTaxNumber(federalTaxNumber);
        Query query = filter.createQuery();

        try {
            return  (DigitalCertificate) query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public DigitalCertificate findByFederalTaxNumberCreatedAt(String federalTaxNumber, Date createdAt) {
        CertificateFilter filter = new CertificateFilter(entityManager);
        filter.filterByFederalTaxNumber(federalTaxNumber);
        filter.filterByCreatedAt(createdAt);

        Query query = filter.createQuery();

        try {
            return (DigitalCertificate) query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DigitalCertificate> findByFederalTaxNumberOrderByValidUntil(String federalTaxNumber) {
        CertificateFilter filter = new CertificateFilter(entityManager);
        filter.filterByFederalTaxNumber(federalTaxNumber);
        filter.filterByValidUntilDesc();

        Query query = filter.createQuery();

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return  null;
        }
    }
}
