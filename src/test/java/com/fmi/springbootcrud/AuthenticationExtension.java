package com.fmi.springbootcrud;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class AuthenticationExtension extends SimpleInstrumentation {

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {

        if (parameters.getExecutionInput().getQuery().startsWith("mutation")) {
            // I want to add here a logged adminUser

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("adminUser", "adminUser",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

            SecurityContextHolder.getContext().setAuthentication(token);
        } else {
            // I want to add here a logged standardUser
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("standardUser", "standardUser",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        return super.beginExecution(parameters);
    }

}