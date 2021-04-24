package com.houkunlin.dao.extend.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.houkunlin.dao.PageVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MyBatisPlus 分页对象工具
 *
 * @author HouKunLin
 */
public class MyBatisPlusPageUtil {
    private MyBatisPlusPageUtil() {
    }

    public static <T> PageVo<T> toPageVo(IPage<T> page) {
        List<PageVo.SortVo> sorts = page.orders()
                .stream()
                .map(MyBatisPlusPageUtil::toPageSortVo)
                .collect(Collectors.toList());

        return new PageVo<>(
                page.getRecords(),
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                page.getCurrent() == 1,
                page.getCurrent() == page.getPages(),
                page.getRecords().isEmpty(),
                sorts);
    }

    public static PageVo.SortVo toPageSortVo(OrderItem order) {
        return new PageVo.SortVo(order.getColumn(), order.isAsc() ? "asc" : "desc");
    }
}
