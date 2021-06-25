package com.houkunlin.dao.extend;

import com.houkunlin.dao.PageVo;
import com.houkunlin.dao.extend.page.PageConverter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HouKunLin
 */
@Component
public class PageUtil {
    private static List<PageConverter> converters;

    public PageUtil(final List<PageConverter> converters) {
        PageUtil.converters = converters;
    }

    public static <T> PageVo<T> toPageVo(Object page) {
        for (final PageConverter converter : converters) {
            if (converter.supportPage(page)) {
                return converter.toPage(page);
            }
        }
        return null;
    }

    public static PageVo.SortVo toPageSortVo(Object order) {
        for (final PageConverter converter : converters) {
            if (converter.supportPageSort(order)) {
                return converter.toPageSort(order);
            }
        }
        return null;
    }
}
