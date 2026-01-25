package top.toobee.spring.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.toobee.spring.repository.UserRepository;
import top.toobee.spring.utils.DynamicTtlCache;
import top.toobee.spring.utils.JwtUtil;
import top.toobee.spring.utils.RolePermissionData;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RolePermissionData rolePermissionData;
    private final DynamicTtlCache dynamicTtlCache;

    @Autowired
    public JwtAuthFilter(
            JwtUtil jwtUtil,
            UserRepository userRepository,
            RolePermissionData rolePermissionData,
            DynamicTtlCache dynamicTtlCache) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.rolePermissionData = rolePermissionData;
        this.dynamicTtlCache = dynamicTtlCache;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            final var jwt = header.substring(7);
            if (dynamicTtlCache.hasToken(jwt)) {
                try {
                    final var username = jwtUtil.extractSubject(jwt);
                    final var opt = userRepository.findRoleIdByName(username);
                    if (opt.isPresent()) {
                        final var context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        username, null, rolePermissionData.getPerms(opt.get())));
                        SecurityContextHolder.setContext(context);
                    } else {
                        logger.warn("User {} not found", username);
                        SecurityContextHolder.clearContext();
                    }
                } catch (JwtException | IllegalArgumentException e) {
                    logger.warn("JWT token parsing error\n", e);
                    SecurityContextHolder.clearContext();
                }
            } else SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
