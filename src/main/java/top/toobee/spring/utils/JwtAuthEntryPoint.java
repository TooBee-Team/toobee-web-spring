package top.toobee.spring.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public final class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            @Nullable HttpServletRequest request,
            HttpServletResponse response,
            @Nullable AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"未登录或登录过期\"}");
    }
}
