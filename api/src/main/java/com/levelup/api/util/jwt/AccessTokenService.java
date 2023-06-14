package com.levelup.api.util.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccessTokenService {

    private final TokenProvider tokenProvider;

    public AccessToken createAccessToken(String role) {
        String token = tokenProvider.createAccessToken(role);
        return AccessToken.of(
                token,
                tokenProvider.getExpiration(token),
                tokenProvider.getIssuedAt(token));
    }
}
