package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 自动配置使用 @PageableDefault 注解来注解 IPage/Page 类型的参数并填充相应的参数信息：当前页码、每页条数、排序字段
 *
 * @author HouKunLin
 */
@Configuration
@EnableTransactionManagement
@ConditionalOnClass(value = {SpringDataWebConfiguration.class, IPage.class})
public class MyBatisPlusExtConfiguration implements WebMvcConfigurer {
    private final PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    public MyBatisPlusExtConfiguration(PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver) {
        if (pageableHandlerMethodArgumentResolver == null) {
            throw new IllegalArgumentException("Invalid PageableHandlerMethodArgumentResolver: Configuration MyBatisPlusExt need PageableHandlerMethodArgumentResolver bean");
        }
        this.pageableHandlerMethodArgumentResolver = pageableHandlerMethodArgumentResolver;
    }

    @ConditionalOnMissingBean
    @Bean
    public PageableToPageHandlerMethodArgumentResolver pageableToPageHandlerMethodArgumentResolver() {
        return new PageableToPageHandlerMethodArgumentResolver(pageableHandlerMethodArgumentResolver);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableToPageHandlerMethodArgumentResolver());
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
    @ConditionalOnMissingBean
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
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
