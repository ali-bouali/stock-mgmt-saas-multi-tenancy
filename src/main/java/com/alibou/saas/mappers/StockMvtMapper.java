package com.alibou.saas.mappers;

import com.alibou.saas.entities.Product;
import com.alibou.saas.entities.StockMvt;
import com.alibou.saas.requests.StockMvtRequest;
import com.alibou.saas.responses.StockMvtResponse;
import org.springframework.stereotype.Component;

@Component
public class StockMvtMapper {

    public StockMvt toEntity(final StockMvtRequest request) {
        return StockMvt.builder()
                       .dateMvt(request.getDateMvt())
                       .comment(request.getComment())
                       .typeMvt(request.getTypeMvt())
                       .quantity(request.getQuantity())
                       .product(Product.builder()
                                       .id(request.getProductId())
                                       .build())
                       .build();
    }

    public StockMvtResponse toResponse(final StockMvt entity) {
        return StockMvtResponse.builder()
                               .dateMvt(entity.getDateMvt())
                               .comment(entity.getComment())
                               .typeMvt(entity.getTypeMvt())
                               .quantity(entity.getQuantity())
                               .build();
    }
}
