package com.alibou.saas.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Tenant - Intercepete CHAQUE reauete HTTP pour identifer le tenant.
 *
 * Ce filtre est point d'entree du mecanisme multi-tenant.
 * Il s'execute avant tous les controleurs et services.
 *
 * Strategie d'identification du tenant (par order de priorite):
 *  1. Header "X-Tenant-ID"
 *  2. (Optionnel) Sous- domaine: alpha.stockapp.com -> "alpha"
 *
 * Si aucun tenant n'est identifie -> reponse 400 BAD REQUEST
 */


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String tenantId = resolveTenant(request);
        if (tenantId == null || tenantId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Tenant ID is missing in the request header, please add the header X-Tenant-ID\"}"
            );
            return;
        }
        try {
            // stocker le tenant dans le ThreadLocal
            TenantContext.setCurrentTenant(tenantId);

            // continuer la chaine de filter -> controller -> service
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // CRITIQUE : toujours nettoyer le ThreadLocal apres la requete
            // Sans ce clear() le tenant pourrait "fuiter" vers la requete suivante
            // si le thread est reutilise par le pool de threads du serveur
            TenantContext.clear();
        }
    }

    private String resolveTenant(final HttpServletRequest request) {
        final String tenantId = request.getHeader(TENANT_HEADER);
        if (tenantId != null && !tenantId.isBlank()) {
            return tenantId.trim().toLowerCase();
        }
        return null;
    }
}
