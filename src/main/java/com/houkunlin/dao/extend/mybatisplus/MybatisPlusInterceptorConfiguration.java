package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import net.sf.jsqlparser.expression.Expression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * 自动配置使用 @PageableDefault 注解来注解 IPage/Page 类型的参数并填充相应的参数信息：当前页码、每页条数、排序字段
 *
 * @author HouKunLin
 */
@Configuration
@EnableTransactionManagement
@ConditionalOnClass(value = {MybatisPlusInterceptor.class})
public class MybatisPlusInterceptorConfiguration {
    /**
     * 数据权限处理器列表
     */
    private final List<DataPermissionHandler> handlers;
    private final long maxPageSize;

    /**
     * 构造方法
     *
     * @param handlers    数据权限处理器列表
     * @param maxPageSize 分页每页最大数量
     * @see SpringDataWebProperties.Pageable#maxPageSize
     */
    public MybatisPlusInterceptorConfiguration(final List<DataPermissionHandler> handlers,
                                               @Value("${spring.data.web.max-page-size:2000}") final int maxPageSize) {
        this.handlers = handlers;
        this.maxPageSize = maxPageSize;
    }

    /**
     * 增加数据权限拦截器配置
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public DataPermissionInterceptor dataPermissionInterceptor() {
        return new DataPermissionInterceptor((where, mappedStatementId) -> {
            Expression last = where;
            for (final DataPermissionHandler expression : handlers) {
                last = expression.getSqlSegment(last, mappedStatementId);
            }
            return last;
        });
    }

    /**
     * 乐观锁配置
     */
    @ConditionalOnMissingBean
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 阻止全表删除: SQL 执行分析拦截器 stopProceed 发现全表执行 delete update 是否停止运行
     */
    @ConditionalOnMissingBean
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 分页插件
     */
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    @ConditionalOnMissingBean
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        final PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
        interceptor.setMaxLimit(maxPageSize);
        return interceptor;
    }

    /**
     * MyBatis Plus 拦截器配置 MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> interceptors) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptors.forEach(interceptor::addInnerInterceptor);
        return interceptor;
    }
}
