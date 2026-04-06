package com.alibou.saas.services.impl;

import com.alibou.saas.entities.Tenant;
import com.alibou.saas.exceptions.TenantProvisioningException;
import com.alibou.saas.services.ProvisioningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvisioningServiceImpl implements ProvisioningService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Override
    public void provisionTenant(final Tenant tenant) {
        final String schemaName = "tenant_" + tenant.getCompanyCode().toLowerCase();

        try {
            log.info("Provisioning tenant: {} (schema: {})", tenant.getCompanyName(), schemaName);
            // 1. Create the Postgres schema
            createSchema(schemaName);
            log.info("Schema created successfully: {}", schemaName);

            // 2. Run Flyway migrations for this schema
            runTenantMigrations(schemaName);
            log.info("Tenant migrations completed successfully for schema: {}", schemaName);

            // 3. Initialize the default data (optional)
            initializeDefaultData(schemaName, tenant);
        } catch (final Exception e) {
            log.error("Failed to provision tenant: {}", tenant.getCompanyName(), e);

            // rollback: drop schema creation
            try {
                dropSchema(schemaName);
            } catch (final Exception exp) {
                log.error("Failed to rollback schema creation for tenant: {}", tenant.getCompanyName(), e);
            }
            throw new TenantProvisioningException("Failed to provision tenant");
        }

    }

    private void dropSchema(final String schemaName) {
        final String sql = String.format("DROP SCHEMA IF EXISTS %s CASCADE", schemaName);
        this.jdbcTemplate.execute(sql);
    }

    private void createSchema(final String schemaName) {
        final String sql = String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName);
        this.jdbcTemplate.execute(sql);
    }

    private void runTenantMigrations(final String schemaName) {
        log.info("Running tenant migrations for schema: {}", schemaName);
        final Flyway tenantFlyway = Flyway.configure()
                .dataSource(this.dataSource)
                .schemas(schemaName)
                .locations("classpath:db/migration/tenant")
                .baselineOnMigrate(true)
                .table("flyway_schema_history")
                .validateOnMigrate(true)
                .cleanDisabled(true)
                .load();

        log.info("Tenant migrations started");
        tenantFlyway.migrate();
        log.info("Tenant migrations completed");
    }

    private void initializeDefaultData(final String schemaName, final Tenant tenant) {
        log.info("Initializing default data for tenant: {}", tenant.getCompanyName());
        // here you can add default data initialization code
    }
}
