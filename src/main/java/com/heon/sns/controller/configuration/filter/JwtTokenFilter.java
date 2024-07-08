package com.heon.sns.controller.configuration.filter;

import com.heon.sns.model.User;
import com.heon.sns.service.UserService;
import com.heon.sns.util.JwtTokenUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs with getting header, header is null or invalid");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            if (JwtTokenUtils.isExpired(token, key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            }

            // JWT Token에서 username을 가져옴.
            String username = JwtTokenUtils.getUserName(token, key);
            // JWT Token에서 가져온 Username을 가지고 여기서 DB를 한번 조회함
            // 이 부분을 캐시를 통해서 가지고 오도록 변경하겠습니다.
            User user = userService.loadUserByUserName(username);

            // User를 UsernamePasswordAuthenticationToken에다가 넣어준다.
            // 여기서는 3가지 파라미터를 받고 있음(principal, credentials, authorities)
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 만든 authentication을 context에 넣어주게 된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            filterChain.doFilter(request, response);
            log.error("Error occurs while validating, {}", e.toString());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
