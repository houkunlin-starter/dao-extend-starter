package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Primary;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通过 @PageableDefault 注解来注解 MyBatisPlus 的 IPage 对象，使用 IPage 对象来接收参数
 *
 * @author HouKunLin
 */
@Component
@Primary
@ConditionalOnClass(value = {SpringDataWebConfiguration.class, IPage.class})
public class PageableToPageHandlerMethodArgumentResolver implements IPageHandlerMethodArgumentResolver {
    private final PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    public PageableToPageHandlerMethodArgumentResolver(@NonNull PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver) {
        this.pageableHandlerMethodArgumentResolver = pageableHandlerMethodArgumentResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return IPage.class.equals(methodParameter.getParameterType());
    }

    /**
     * @see PageableHandlerMethodArgumentResolver#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)
     */
    @Override
    public IPage<?> resolveArgument(@NonNull MethodParameter methodParameter,
                                    ModelAndViewContainer mavContainer,
                                    @NonNull NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) throws Exception {
        Pageable pageable = pageableHandlerMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        // pageable.getPageNumber 是从 0 开始计数的，因此要 + 1
        Page<?> page = new Page<>(pageable.getPageNumber() + 1L, pageable.getPageSize());

        Sort orders = pageable.getSort();
        if (!orders.isSorted()) {
            return page;
        }

        List<OrderItem> orderItems = orders.stream().map(order -> {
            if (order.getDirection() == Sort.Direction.DESC) {
                return OrderItem.desc(order.getProperty());
            }
            return OrderItem.asc(order.getProperty());
        }).collect(Collectors.toList());
        page.addOrder(orderItems);

        return page;
    }
}
