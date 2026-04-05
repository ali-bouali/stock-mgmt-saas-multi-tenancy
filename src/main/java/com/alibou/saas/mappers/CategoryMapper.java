package com.alibou.saas.mappers;

import com.alibou.saas.entities.Category;
import com.alibou.saas.requests.CategoryRequest;
import com.alibou.saas.responses.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(final CategoryRequest request) {
        return Category.builder()
                       .name(request.getName())
                       .description(request.getDescription())
                       .build();
    }

    public CategoryResponse toResponse(final Category entity) {
        final int nbProduct = 0;// entity.getProducts() == null ? 0 : entity.getProducts().size();
        return CategoryResponse.builder()
                               .name(entity.getName())
                               .description(entity.getDescription())
                               .nbProducts(nbProduct)
                               .build();
    }
}
