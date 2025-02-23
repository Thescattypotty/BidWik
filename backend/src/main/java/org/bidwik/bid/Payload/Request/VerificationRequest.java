package org.bidwik.bid.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(
    @Email(message = "Email should be valid")
    String email,
    @NotBlank(message = "Code cannot be blank")
    String code
) {
    
}
