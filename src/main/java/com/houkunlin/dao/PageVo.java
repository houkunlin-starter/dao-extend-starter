package com.houkunlin.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页返回对象
 *
 * @author HouKunLin
 */
@ApiModel("分页返回对象")
@ToString
@Getter
@AllArgsConstructor
public class PageVo<T> {
    /**
     * 列表内容
     */
    @ApiModelProperty("列表内容")
    private final List<T> list;
    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private final long page;
    /**
     * 每页数据数量
     */
    @ApiModelProperty("每页数据数量")
    private final long size;
    /**
     * 数据总数量
     */
    @ApiModelProperty("数据总数量")
    private final long total;
    /**
     * 是否是第一页
     */
    @ApiModelProperty("是否是第一页")
    private final boolean first;
    /**
     * 是否是最后一页
     */
    @ApiModelProperty("是否是最后一页")
    private final boolean last;
    /**
     * list 是否有数据，返回的列表是否为空
     */
    @ApiModelProperty("返回的列表是否为空")
    private final boolean empty;
    /**
     * 排序信息
     */
    @ApiModelProperty("排序信息")
    private final List<SortVo> sort;
    /**
     * 扩展数据，除了 list 列表之外的有效的扩展数据
     */
    @ApiModelProperty("扩展数据，除了 list 列表之外的有效的扩展数据")
    private final LinkedHashMap<String, Object> extension = new LinkedHashMap<>();

    /**
     * 增加扩展数据
     *
     * @param key   扩展数据的KEY
     * @param value 扩展数据内容
     * @return 当前对象
     */
    public PageVo<T> put(String key, Object value) {
        extension.put(key, value);
        return this;
    }

    /**
     * 增加扩展数据
     *
     * @param map 扩展数据内容
     * @return 当前对象
     */
    public PageVo<T> putAll(Map<? extends String, ?> map) {
        extension.putAll(map);
        return this;
    }

    @Getter
    @AllArgsConstructor
    public static class SortVo {
        /**
         * 排序字段
         */
        @ApiModelProperty("排序字段")
        private final String field;
        /**
         * 排序方式
         */
        @ApiModelProperty("排序方式")
        private final String direction;
    }
}
