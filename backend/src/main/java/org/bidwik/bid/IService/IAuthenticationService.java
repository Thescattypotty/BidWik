package org.bidwik.bid.IService;

import org.bidwik.bid.Payload.Request.LoginRequest;
import org.bidwik.bid.Payload.Request.RegisterRequest;
import org.bidwik.bid.Payload.Request.VerificationRequest;
import org.bidwik.bid.Payload.Response.LoginResponse;
import org.bidwik.bid.Payload.Response.RegisterResponse;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse verify(VerificationRequest verificationRequest);
}
