package ru.vsouth.springbootpractice.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsouth.springbootpractice.config.UserInfoUserDetails;
import ru.vsouth.springbootpractice.config.UserInfoUserDetailsService;
import ru.vsouth.springbootpractice.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserInfoUserDetailsService userService;

    public JwtAuthFilter(JwtService jwtService, UserInfoUserDetailsService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public JwtService getJwtService() {
        return jwtService;
    }

    public UserInfoUserDetailsService getUserService() {
        return userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(7);
        if (jwtToken.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Токен пуст");
            filterChain.doFilter(request, response);
            return;
        }
        if (jwtService.isTokenValid(jwtToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtService.extractUsername(jwtToken);
            UserInfoUserDetails userDetails = userService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Токен некорректный");
        }


        filterChain.doFilter(request, response);
    }
}
