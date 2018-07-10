package org.bcbs.systemcore.lib.auth.provider.endpoint;

import org.bcbs.systemcore.lib.auth.annotation.AuthEndpoint;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class AuthEndpointHandlerMapping extends RequestMappingHandlerMapping {

    public AuthEndpointHandlerMapping() {
        setOrder(Ordered.LOWEST_PRECEDENCE - 2);
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotationUtils.findAnnotation(beanType, AuthEndpoint.class) != null;
    }
}
