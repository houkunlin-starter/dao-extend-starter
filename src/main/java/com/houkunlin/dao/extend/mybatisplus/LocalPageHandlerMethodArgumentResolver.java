package com.houkunlin.dao.extend.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HouKunLin
 */
@Data
@Component
@ConditionalOnClass(value = {IPage.class})
public class LocalPageHandlerMethodArgumentResolver implements IPageHandlerMethodArgumentResolver {
    @Value("${spring.data.web.pageable.max-page-size:2000}")
    private int maxPageSize = 2000;

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return IPage.class.equals(methodParameter.getParameterType());
    }

    @Override
    public IPage<?> resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {

        String page = webRequest.getParameter("page");
        String pageSize = webRequest.getParameter("size");
        String[] directionParameter = webRequest.getParameterValues("sort");

        Page<?> objectPage = new Page<>(parseInt(page, Integer.MAX_VALUE), parseInt(pageSize, maxPageSize));

        if (directionParameter != null && directionParameter.length > 0) {
            List<OrderItem> orderItems = new ArrayList<>();
            for (final String sort : directionParameter) {
                if (!StringUtils.hasText(sort)) {
                    continue;
                }
                final String[] split = sort.split(",");
                if (split.length == 1) {
                    orderItems.add(OrderItem.asc(split[0]));
                } else {
                    switch (split[1].toUpperCase()) {
                        case "DESC":
                            orderItems.add(OrderItem.desc(split[0]));
                            break;
                        case "ASC":
                        default:
                            orderItems.add(OrderItem.asc(split[0]));
                    }
                }
            }
            objectPage.addOrder(orderItems);
        }

        return objectPage;
    }

    private int parseInt(String source, int max) {
        try {
            final int parseInt = Integer.parseInt(source);
            return Math.min(parseInt, max);
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
