package org.bcbs.microservice.oauth.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bcbs.microservice.controller.GenericResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException ex) throws IOException, ServletException {
        new ObjectMapper().writeValue(httpServletResponse.getWriter(),
                GenericResponse.failure(ex.getMessage()));
    }
}
