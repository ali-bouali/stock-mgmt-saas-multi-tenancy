package com.alibou.saas.services;

import com.alibou.saas.common.PageResponse;
import com.alibou.saas.requests.StockMvtRequest;
import com.alibou.saas.responses.StockMvtResponse;

public interface StockMvtService extends BasicService<StockMvtRequest, StockMvtResponse> {

    PageResponse<StockMvtResponse> findAllByProductId(final String productId, final int page, final int size);
}
