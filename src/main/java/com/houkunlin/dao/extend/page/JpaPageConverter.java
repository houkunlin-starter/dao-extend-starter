package com.houkunlin.dao.extend.page;

import com.houkunlin.dao.PageVo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * @author HouKunLin
 */
@ConditionalOnClass(Page.class)
@Component
public class JpaPageConverter implements PageConverter {
    @Override
    public boolean supportPage(final Object page) {
        return Page.class.isAssignableFrom(page.getClass());
    }

    @Override
    public boolean supportPageSort(final Object sort) {
        return Sort.Order.class.isAssignableFrom(sort.getClass());
    }

    @Override
    public <T> PageVo<T> toPage(final Object page) {
        return JpaPageUtil.toPageVo((Page<T>) page);
    }

    @Override
    public PageVo.SortVo toPageSort(final Object sort) {
        return JpaPageUtil.toPageSortVo((Sort.Order) sort);
    }
}
