package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author HouKunLin
 */
public interface LambdaQuery<T> {
    /**
     * 查询条件构建
     *
     * @param wrapper LambdaQueryChainWrapper
     * @return LambdaQueryChainWrapper
     */
    default LambdaQueryChainWrapper<T> queryBuilder(LambdaQueryChainWrapper<T> wrapper) {
        return wrapper;
    }

    /**
     * 查询条件构建
     *
     * @param wrapper QueryChainWrapper
     * @param <E>     泛型
     * @return QueryChainWrapper
     */
    default <E> QueryChainWrapper<E> queryBuilder(QueryChainWrapper<E> wrapper) {
        return wrapper;
    }

    /**
     * 排序参数构建
     *
     * @param wrapper QueryChainWrapper
     * @param page    分页
     * @param <E>     泛型
     * @return QueryChainWrapper
     */
    default <E> QueryChainWrapper<E> orderBuilder(QueryChainWrapper<E> wrapper, IPage<E> page) {
        final List<OrderItem> orders = page.orders();
        for (final OrderItem order : orders) {
            wrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        return wrapper;
    }

    /**
     * 增加查询条件
     *
     * @param <V>   查询值的类型
     * @param field 需要查询的字段
     * @param value 查询的值
     * @param build LambdaQueryChainWrapper 的查询条件方法
     */
    default <V> void addQuery(SFunction<T, V> field, Object value, BiConsumer<SFunction<T, V>, Object> build) {
        if (value != null) {
            build.accept(field, value);
        }
    }

    /**
     * 增加查询条件
     *
     * @param field 需要查询的字段
     * @param value 查询的值
     * @param <K>   字段类型泛型
     * @param build QueryChainWrapper 的查询条件方法
     */
    default <K> void addQuery(K field, Object value, BiConsumer<K, Object> build) {
        if (value != null) {
            build.accept(field, value);
        }
    }

    /**
     * 查询条件构建
     *
     * @param service IService<T>
     * @return LambdaQueryChainWrapper<T>
     */
    default LambdaQueryChainWrapper<T> lambdaQuery(IService<T> service) {
        return queryBuilder(service.lambdaQuery());
    }

    /**
     * 查询条件构建
     *
     * @param service IService<T>
     * @return LambdaQueryChainWrapper<T>
     */
    default QueryChainWrapper<T> query(IService<T> service) {
        return queryBuilder(service.query());
    }

    /**
     * 查询条件构建
     *
     * @param service IService<T>
     * @param orders  IPage<T>
     * @return LambdaQueryChainWrapper<T>
     */
    default QueryChainWrapper<T> query(IService<T> service, IPage<T> orders) {
        final QueryChainWrapper<T> queryBuilder = queryBuilder(service.query());
        return orderBuilder(queryBuilder, orders);
    }
}
