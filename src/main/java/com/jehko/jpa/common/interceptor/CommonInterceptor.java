package com.jehko.jpa.common.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.jehko.jpa.common.exception.AuthFailException;
import com.jehko.jpa.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("[Common Interceptor] - preHandle Start");
        String requestUri = request.getRequestURI();
        log.info("{} {}", request.getMethod(), requestUri);

        if(!validJWT(request)) {
            throw new AuthFailException("인증 정보가 정확하지 않습니다.");
        }

        return true;
    }

    private boolean validJWT(HttpServletRequest request) {
        String token = request.getHeader("J_TOKEN");

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return false;
        }

        return true;
    }
}
