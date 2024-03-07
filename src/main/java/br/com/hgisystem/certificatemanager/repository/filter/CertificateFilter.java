package br.com.hgisystem.certificatemanager.repository.filter;

import br.com.hgisystem.certificatemanager.infra.helpers.ExecuteQueryHelper;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;

public class CertificateFilter extends BaseFilter{
    private static final String baseQuery = "SELECT f FROM DigitalCertificate f WHERE 1=1";
    private static final String queryCount = "SELECT COUNT(c) FROM DigitalCertificate c";
    public CertificateFilter(EntityManager entityManager) {
        super(entityManager, baseQuery);
    }

    public void filterByFederalTaxNumber(String federalTaxNumber) {
        query.append(" AND f.federalTaxNumber = :federalTaxNumber");
        params.put("federalTaxNumber", federalTaxNumber);
    }

    public void filterByValidUntilDesc() {
        query.append(" ORDER BY f.validUntil DESC");
    }

    public void filterByCreatedAt(Date createdAt) {
        query.append(" AND f.createdAt = :createdAt");
        params.put("createdAt", createdAt);
    }

    public Query createQuery() {
        return ExecuteQueryHelper.execute(DigitalCertificate.class, entityManager, query.toString(), params);
    }

    public Query createQueryCount() {
        return ExecuteQueryHelper.execute(Long.class, entityManager, queryCount, params);
    }
}
