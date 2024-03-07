package br.com.hgisystem.certificatemanager.infra.usercontext.impl;

import br.com.hgisystem.certificatemanager.core.usercontext.JwtUserContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Builder
@AllArgsConstructor
public class ApplicationUserContext implements JwtUserContext {

    private JwtAuthenticationToken jwtAuthenticationToken;

    @Override
    public Instant getExp() {
        return jwtAuthenticationToken.getToken().getExpiresAt();
    }

    @Override
    public Instant getIat() {
        return jwtAuthenticationToken.getToken().getIssuedAt();
    }

    @Override
    public String getIssuer() {
        return jwtAuthenticationToken.getToken().getIssuer().toString();
    }

    @Override
    public String getSubject() {
        return jwtAuthenticationToken.getToken().getSubject();
    }

    @Override
    public Set<String> getRoles() {
        return null;
    }

    @Override
    public String getName() {
        return jwtAuthenticationToken.getName();
    }

    @Override
    public Map<String, Object> getClaims() {
        return jwtAuthenticationToken.getToken().getClaims();
    }
}
