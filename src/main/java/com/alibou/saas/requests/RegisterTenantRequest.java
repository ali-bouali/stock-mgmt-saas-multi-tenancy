package com.alibou.saas.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterTenantRequest {

    @NotBlank(message = "Company name should not be empty")
    private String companyName;
    @NotBlank(message = "Company code should not be empty")
    private String companyCode;
    @NotBlank(message = "Email should not be empty")
    private String email;
    @NotBlank(message = "Admin full name should not be empty")
    private String adminFullName;
    @NotBlank(message = "Admin email should not be empty")
    private String adminEmail;
    @NotBlank(message = "Admin username should not be empty")
    private String adminUsername;
    @NotBlank(message = "Admin password should not be empty")
    private String adminPassword;
}
