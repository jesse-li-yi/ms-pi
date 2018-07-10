package org.bcbs.systemcore.lib.auth.annotation;

import org.bcbs.systemcore.lib.auth.config.AuthServerEndpointsConfiguration;
import org.bcbs.systemcore.lib.auth.config.AuthServerSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Import(value = {AuthServerSecurityConfiguration.class, AuthServerEndpointsConfiguration.class})
public @interface EnableAuthServer {
}
