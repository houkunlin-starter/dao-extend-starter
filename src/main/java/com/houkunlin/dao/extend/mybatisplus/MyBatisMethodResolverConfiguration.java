package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 配置方法参数处理器
 *
 * @author HouKunLin
 */
@AllArgsConstructor
@Configuration
@ConditionalOnClass(value = {MybatisPlusInterceptor.class})
public class MyBatisMethodResolverConfiguration implements WebMvcConfigurer {
    private final IPageHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableHandlerMethodArgumentResolver);
    }
}
