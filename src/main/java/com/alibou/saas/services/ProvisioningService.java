package com.alibou.saas.services;

import com.alibou.saas.entities.Tenant;

public interface ProvisioningService {

    void provisionTenant(final Tenant tenant);
}
