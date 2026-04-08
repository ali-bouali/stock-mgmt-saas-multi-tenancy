package com.alibou.saas.mappers;

import com.alibou.saas.entities.Tenant;
import com.alibou.saas.requests.RegisterTenantRequest;
import com.alibou.saas.responses.TenantResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TenantMapper {

    public Tenant toEntity(final RegisterTenantRequest request) {
        return Tenant.builder()
                     .companyName(request.getCompanyName())
                     .companyCode(request.getCompanyCode())
                     .createdAt(LocalDateTime.now())
                     .email(request.getEmail())
                     .adminFullName(request.getAdminFullName())
                     .adminEmail(request.getAdminEmail())
                     .adminUsername(request.getAdminUsername())
                     .deleted(false)
                     .build();
    }

    public TenantResponse toResponse(final Tenant tenant) {
        return TenantResponse.builder()
                             .tenantId(tenant.getId())
                             .companyName(tenant.getCompanyName())
                             .companyCode(tenant.getCompanyCode())
                             .createdAt(tenant.getCreatedAt())
                             .email(tenant.getEmail())
                             .adminFullName(tenant.getAdminFullName())
                             .adminEmail(tenant.getAdminEmail())
                             .adminUsername(tenant.getAdminUsername())
                             .status(tenant.getStatus())
                             .build();
    }
}
