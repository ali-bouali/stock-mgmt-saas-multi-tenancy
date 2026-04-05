package com.alibou.saas.config;

/**
 * TenantContext - Stocke l'identifiant du tenant courant dans un ThreadLocal.
 *
 * Chaque requete HTTP est traitee par un thread dedie.
 * Le ThreadLocal garantit que le tenant_id est isole par thread,
 * meme en cas de requete simultanee de tenants differents.
 *
 * Flux:
 *    1. TenantFilter extrait le tenant_id de la requete HTTP
 *    2. TenantFilter appelle TenantContext.setCurrentTenant(tenant_id)
 *    3. Le code metier (service, repositories) accede au tenant via TenantContext.getCurrentTenant()
 *    4. TenantFilter appelle TenantContext.clear() apres la reponse (nettoyage)
 *
 */
public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();


    /**
     * Definit l'identifiant du tenant pour le thread courant.
     */
    public static void setCurrentTenant(final String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    /**
     * Recupere l'identifiant du tenant pour le thread courant.
     */
    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    /**
     * Nettoie le tenant du thread courant.
     * IMPORTANT: doit etre appele dans un bloc finally
     * pour eviter les fuites de memoire (memory leak)
     * et les fuites de donnees entre requetes HTTP.
     */
    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
