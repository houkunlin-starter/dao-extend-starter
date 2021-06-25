package com.houkunlin.dao.extend.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.houkunlin.dao.PageVo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author HouKunLin
 */
@ConditionalOnClass(IPage.class)
@Component
public class MyBatisPlusPageConverter implements PageConverter {
    @Override
    public boolean supportPage(final Object page) {
        return IPage.class.isAssignableFrom(page.getClass());
    }

    @Override
    public boolean supportPageSort(final Object sort) {
        return OrderItem.class.isAssignableFrom(sort.getClass());
    }

    @Override
    public <T> PageVo<T> toPage(final Object page) {
        return MyBatisPlusPageUtil.toPageVo((IPage<T>) page);
    }

    @Override
    public PageVo.SortVo toPageSort(final Object sort) {
        return MyBatisPlusPageUtil.toPageSortVo((OrderItem) sort);
    }
}
