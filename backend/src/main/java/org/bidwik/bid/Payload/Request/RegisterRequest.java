package org.bidwik.bid.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @Email(message = "Email should be valid")
    String email,
    @NotBlank(message = "First name is required")
    String firstName,
    @NotBlank(message = "Last name is required")
    String lastName,
    @NotBlank(message = "Phone number is required")
    String phone,
    String profilePicture,
    @NotBlank(message = "Password is required")
    String password,
    boolean is2FAEnabled
) {
    
}
