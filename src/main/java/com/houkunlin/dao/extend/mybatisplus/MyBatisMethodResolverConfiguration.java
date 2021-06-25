package com.houkunlin.dao.extend.mybatisplus;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author HouKunLin
 */
@AllArgsConstructor
@Configuration
public class MyBatisMethodResolverConfiguration implements WebMvcConfigurer {
    private final IPageHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableHandlerMethodArgumentResolver);
    }
}
