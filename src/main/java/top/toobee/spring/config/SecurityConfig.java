package top.toobee.spring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity //开启WebSecurity功能
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/login","/static/**").permitAll() //允许匿名访问
                .anyRequest().authenticated() //其他的请求则需要验证
        )
                .formLogin(form -> form.loginProcessingUrl("/login").successHandler((req,resp,auth)-> {
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.getWriter().write("{\"msg\":\"登录成功\",\"username\":\"" + auth.getName() + "\"}");
                }).failureHandler((req,resp,ex)->{
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.getWriter().write("{\"msg\":\"登录失败\"}");
                })).logout(logout ->logout.logoutSuccessHandler((req,resp,auth)->{
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.getWriter().write("{\"msg\":\"退出成功\"}");
                })).exceptionHandling(exception ->exception.authenticationEntryPoint((req,resp,ex)->{
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.setStatus(401);
                    resp.getWriter().write("{\"msg\":\"未登录\"}");
                }));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173"); //前端地址
        config.setAllowCredentials(true);//允许携带cookie
        config.addAllowedHeader("*"); //允许所有的请求头
        config.addAllowedMethod("*"); //允许所有的 HTTP 方法 GET POST PUT DELETE
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
