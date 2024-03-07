package br.com.hgisystem.certificatemanager.repository.filter;

import br.com.hgisystem.certificatemanager.core.exception.FilterNotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;

public abstract class BaseFilter {
    protected StringBuilder query;
    protected EntityManager entityManager;
    protected HashMap<String, Object> params;

    public BaseFilter(EntityManager entityManager, String query) {
        this.query = new StringBuilder(query);
        this.entityManager = entityManager;
        this.params = new HashMap<>();
    }

    public Query createQuery() throws FilterNotImplementedException {
        throw new FilterNotImplementedException();
    }

    public Query createQueryCount() throws FilterNotImplementedException {
        throw new FilterNotImplementedException();
    }
}
