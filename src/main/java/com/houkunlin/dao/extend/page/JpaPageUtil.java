package com.houkunlin.dao.extend.page;

import com.houkunlin.dao.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页对象工具
 *
 * @author HouKunLin
 */
public class JpaPageUtil {
    private JpaPageUtil() {
    }

    public static <T> PageVo<T> toPageVo(Page<T> page) {
        final List<PageVo.SortVo> sorts = page.getPageable()
                .getSort()
                .stream()
                .map(JpaPageUtil::toPageSortVo)
                .collect(Collectors.toList());
        return new PageVo<>(
                page.getContent(),
                // page.getNumber() 是相对于数据库的分页偏移量，从0开始计数的
                page.getNumber() + 1L,
                page.getSize(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                sorts);
    }

    public static PageVo.SortVo toPageSortVo(Sort.Order order) {
        return new PageVo.SortVo(order.getProperty(), order.getDirection().name().toLowerCase());
    }
}
