package org.bidwik.bid.Payload.Response;

public record RegisterResponse(
    String accessToken,
    String refreshToken,
    boolean is2FAEnabled,
    String secretImageUri
) {
    
}
