package com.alibou.saas.services;

import com.alibou.saas.common.PageResponse;
import org.springframework.data.domain.Page;

public interface BasicService<I, O> {

    void create(final I request);

    void update(final String id, final I request);

    PageResponse<O> findAll(final int page, final int size);

    O findById(final String id);

    void delete(final String id);
}
