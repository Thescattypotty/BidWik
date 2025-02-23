package org.bidwik.bid.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email(message = "Email should be valid")
    String email,
    @NotBlank(message = "Password cannot be blank")
    String password
) {
    
}
