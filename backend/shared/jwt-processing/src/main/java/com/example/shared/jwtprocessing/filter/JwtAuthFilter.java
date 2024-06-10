package com.example.shared.jwtprocessing.filter;

import com.example.shared.jwtprocessing.service.JwtService;
import com.example.shared.jwtprocessing.constants.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final List<String> excludedPaths;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        log.debug("doFilterInternal: start filter");

        String bearerTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.debug("doFilterInternal: bearerTokenHeader {}}", bearerTokenHeader);

        if(bearerTokenHeader == null || !bearerTokenHeader.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
            var ex = new RuntimeException("Bearer token not specified");
            handlerExceptionResolver.resolveException(request, response, null, ex);
            return;
        }

        String bearerToken = bearerTokenHeader.substring(7);

        try {
            jwtService.validateToken(bearerToken);
            log.debug("doFilterInternal: token is valid");

            if(SecurityContextHolder.getContext().getAuthentication() != null) {
                log.error("Authentication is not null!");
            }

            var authentication = new UsernamePasswordAuthenticationToken(
                    jwtService.extractUser(bearerToken),
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority(jwtService.extractRole(bearerToken)))
            );

            log.debug("doFilterInternal: authentication: {}", authentication);
            log.debug("doFilterInternal: principal: {}", authentication.getPrincipal());

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            log.debug("doFilterInternal: principal: {}", authentication.getPrincipal());

            log.debug("doFilterInternal: before doFilter");

            filterChain.doFilter(request, response);

            log.debug("doFilterInternal: after doFilter");
        } catch (Exception ex) {
            log.debug("doFilterInternal: Exception ex: {}", ex.getMessage());

            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        log.debug("shouldNotFilter: excludedPaths: {}", excludedPaths);
        return excludedPaths.stream()
                .anyMatch(p -> new AntPathMatcher().match(p, request.getServletPath()));
    }
}
