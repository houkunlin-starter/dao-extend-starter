package com.houkunlin.dao.extend.page;

import com.houkunlin.dao.PageVo;

/**
 * 分页视图数据转换器
 *
 * @author HouKunLin
 */
public interface PageConverter {
    /**
     * 判断是否支持转换该中类型的数据
     *
     * @param page 分页对象
     * @return 结果
     */
    boolean supportPage(Object page);

    /**
     * 判断是否支持转换该中类型的数据
     *
     * @param sort 排序对象
     * @return 结果
     */
    boolean supportPageSort(Object sort);

    /**
     * 转换成分页视图对象
     *
     * @param page 分页对象
     * @param <T>  泛型
     * @return 视图对象
     */
    <T> PageVo<T> toPage(Object page);

    /**
     * 转换成排序视图对象
     *
     * @param sort 排序对象
     * @return 视图对象
     */
    PageVo.SortVo toPageSort(Object sort);
}
