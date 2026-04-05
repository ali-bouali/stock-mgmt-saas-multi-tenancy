package com.alibou.saas.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name should not be empty")
    @Size(min = 3, max = 255, message = "Product name should be between 3 and 255 characters")
    private String name;
    @NotBlank(message = "Product reference should not be empty")
    @Size(min = 3, max = 255, message = "Product reference should be between 3 and 255 characters")
    private String reference;
    private String description;
    @Positive(message = "Alert threshold should be a positive number")
    private Integer alertThreshold;
    @Positive(message = "Price should be a positive number")
    private BigDecimal price;
    @NotBlank(message = "Category ID should not be empty")
    private String categoryId;
}
