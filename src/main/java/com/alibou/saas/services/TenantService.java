package com.alibou.saas.services;

import com.alibou.saas.common.PageResponse;
import com.alibou.saas.requests.RegisterTenantRequest;
import com.alibou.saas.responses.TenantResponse;

public interface TenantService {

    void registerTenant(final RegisterTenantRequest request);

    void approveTenant(final String tenantId);

    void activateTenant(final String tenantId);

    void deactivateTenant(final String tenantId);

    void suspendTenant(final String tenantId);

    PageResponse<TenantResponse> findAll(final int page, final int size);
}
