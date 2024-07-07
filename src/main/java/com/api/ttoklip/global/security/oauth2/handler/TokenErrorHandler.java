//package com.api.ttoklip.global.security.auth.handler;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TokenErrorHandler implements AccessDeniedHandler {
//    // 권한 부족시 호출되는 핸들러
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
//        response.sendError(HttpServletResponse.SC_FORBIDDEN);
//    }
//}
