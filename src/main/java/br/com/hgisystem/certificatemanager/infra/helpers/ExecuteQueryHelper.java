package br.com.hgisystem.certificatemanager.infra.helpers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public abstract class ExecuteQueryHelper {
    public static Query execute(Class c, EntityManager entityManager, String commandQuery, HashMap<String, Object> params){
        Query query = entityManager.createQuery(commandQuery, c);

        for (Map.Entry<String,Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query;
    }
}
