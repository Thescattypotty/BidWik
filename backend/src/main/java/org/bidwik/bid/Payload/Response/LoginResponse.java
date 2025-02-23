package org.bidwik.bid.Payload.Response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record LoginResponse(
    String accessToken,
    String refreshToken,
    boolean is2FAEnabled
) {
    
}
