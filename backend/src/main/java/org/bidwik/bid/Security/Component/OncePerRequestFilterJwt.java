package org.bidwik.bid.Security.Component;

import java.io.IOException;
import java.util.Optional;

import org.bidwik.bid.Security.Service.BlackListService;
import org.bidwik.bid.Security.Service.UserDetailsServiceImpl;
import org.bidwik.bid.Util.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OncePerRequestFilterJwt extends OncePerRequestFilter{
    
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final BlackListService blackListService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
    
        Optional.ofNullable(request.getHeader("Authorization"))
            .filter(header -> header.startsWith("Bearer "))
            .map(header -> header.substring(7))
            .map(String::trim)
            .ifPresentOrElse(
                token -> {
                    if (blackListService.isTokenBlackListed(token) || !jwtUtil.validateToken(token)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    String username = jwtUtil.extractSubject(token);
                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                        );

                        authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                    try {
                        filterChain.doFilter(request, response);
                    } catch (IOException | ServletException e) {
                        e.printStackTrace();
                    }
                },
                () -> {
                    try {
                        filterChain.doFilter(request, response);
                    } catch (IOException | ServletException e) {
                        e.printStackTrace();
                    }
                }
            );
    }
    
}
