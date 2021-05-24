package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author HouKunLin
 */
@Configuration
@ConditionalOnClass(value = {SpringDataWebConfiguration.class, IPage.class})
public class MyBatisMethodResolverConfiguration implements WebMvcConfigurer {
    private final PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    public MyBatisMethodResolverConfiguration(final PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver) {
        if (pageableHandlerMethodArgumentResolver == null) {
            throw new IllegalArgumentException("Invalid PageableHandlerMethodArgumentResolver: Configuration MyBatisPlusExt need PageableHandlerMethodArgumentResolver bean");
        }
        this.pageableHandlerMethodArgumentResolver = pageableHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableToPageHandlerMethodArgumentResolver(pageableHandlerMethodArgumentResolver));
    }
}
