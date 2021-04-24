package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

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
    LambdaQueryChainWrapper<T> queryBuilder(LambdaQueryChainWrapper<T> wrapper);

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
     * 查询条件构建
     *
     * @param service IService<T>
     * @return LambdaQueryChainWrapper<T>
     */
    default LambdaQueryChainWrapper<T> queryBuilder(IService<T> service) {
        return queryBuilder(service.lambdaQuery());
    }
}
