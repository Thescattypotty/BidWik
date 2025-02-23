package org.bidwik.bid.Security.Component;

import java.util.Optional;

import org.bidwik.bid.Security.Service.BlackListService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogoutHandlerJwt implements LogoutHandler{
    private final BlackListService blackListService;

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
        ) {
        Optional.ofNullable(request.getHeader("Authorization"))
            .filter(header -> header.startsWith("Bearer "))
            .map(header -> header.substring(7))
            .map(String::trim)
            .ifPresent(token -> blackListService.addTokenToBlackList(token));
    }
    
}
