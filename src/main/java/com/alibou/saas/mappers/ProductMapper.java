package com.alibou.saas.mappers;

import com.alibou.saas.entities.Category;
import com.alibou.saas.entities.Product;
import com.alibou.saas.requests.ProductRequest;
import com.alibou.saas.responses.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(final ProductRequest request) {
        return Product.builder()
                      .name(request.getName())
                      .reference(request.getReference())
                      .description(request.getDescription())
                      .price(request.getPrice())
                      .alertThreshold(request.getAlertThreshold())
                      .category(Category.builder()
                                        .id(request.getCategoryId())
                                        .build())
                      .deleted(false)
                      .build();
    }

    public ProductResponse toResponse(final Product product) {
        return ProductResponse.builder()
                              .id(product.getId())
                              .name(product.getName())
                              .reference(product.getReference())
                              .description(product.getDescription())
                              .price(product.getPrice())
                              .alertThreshold(product.getAlertThreshold())
                              .categoryId(product.getCategory()
                                                 .getId())
                              .categoryName(product.getCategory()
                                                   .getName())
                              // .availableQuantity() to be later implemented
                              .build();
    }
}
