package br.com.hgisystem.certificatemanager.core.usercontext;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

public interface JwtUserContext {
    Instant getExp();
    Instant getIat();
    String getIssuer();
    String getSubject();
    Set<String> getRoles();
    String getName();
    Map<String, Object> getClaims();
}
