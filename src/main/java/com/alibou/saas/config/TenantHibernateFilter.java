package com.alibou.saas.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * TenantHibernateFilter — Active automatiquement le filtre Hibernate
 * avant chaque appel aux repositories Spring Data JPA.
 *
 * FONCTIONNEMENT :
 *   1. TenantFilter (HTTP) a déjà stocké le tenantId dans TenantContext
 *   2. Cet aspect intercepte tout appel à un Repository
 *   3. Il active le filtre Hibernate "tenantFilter" avec le tenantId courant
 *   4. Hibernate ajoute automatiquement WHERE tenant_id = :tenantId
 *
 * POURQUOI UN ASPECT ?
 *   Sans cet aspect, il faudrait activer le filtre manuellement dans chaque
 *   méthode de service. L'aspect le fait automatiquement et de manière transversale.
 *
 * ALTERNATIVE :
 *   On pourrait aussi utiliser un HandlerInterceptor ou un @EventListener.
 *   L'aspect est plus propre car il s'exécute au plus proche de la couche données.
 */

@Aspect
@Component
public class TenantHibernateFilter {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Intercepte tous les appels aux méthodes des services
     * dans le package com.alibou.saas.services
     *
     * execution(* com.alibou.saas.services.*.*(..))
     *   → toute méthode (*), de toute classe (*), avec tout argument (..)
     *   → dans le package com.alibou.saas.services
     */
    @Before("execution(* com.alibou.saas.services.*.*(..))")
    public void activateTenantFilter() {
        final String tenantId = TenantContext.getCurrentTenant();

        if (tenantId != null) {
            final Session session = this.entityManager.unwrap(Session.class);

            // active le filter et injecte le parametre tenantId
            session.enableFilter("tenantFilter")
                    .setParameter("tenantId", tenantId);
        }
    }
}
