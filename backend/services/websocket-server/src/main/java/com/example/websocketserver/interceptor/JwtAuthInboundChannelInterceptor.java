package com.example.websocketserver.interceptor;

import com.example.shared.jwtprocessing.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static com.example.shared.jwtprocessing.constants.SecurityConstants.BEARER_TOKEN_PREFIX;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthInboundChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//        if(headerAccessor != null && headerAccessor.getCommand() != null &&
//                headerAccessor.getCommand().equals(StompCommand.CONNECT)) {
//            String bearerToken = headerAccessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
//
//            log.debug("preSend: bearerToken {}}", bearerToken);
//
//            try {
//                if(bearerToken == null || !bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
//                    throw new RuntimeException("Bearer token not specified");
//                }
//                bearerToken = bearerToken.substring(7);
//
//                jwtService.validateToken(bearerToken);
//                log.debug("preSend: token is valid");
//
//                if(SecurityContextHolder.getContext().getAuthentication() != null) {
//                    log.error("preSend: Authentication is not null!");
//                }
//
//                var authentication = new UsernamePasswordAuthenticationToken(
//                        jwtService.extractUser(bearerToken),
//                        null,
//                        Collections.singletonList(new SimpleGrantedAuthority(jwtService.extractRole(bearerToken)))
//                );
//
//                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                securityContext.setAuthentication(authentication);
//                SecurityContextHolder.setContext(securityContext);
//
//            } catch (Exception ex) {
//                log.debug("preSend: Exception: {}", ex.getMessage());
//                throw new RuntimeException(ex);
//            }
//        }
        return message;
    }

}
