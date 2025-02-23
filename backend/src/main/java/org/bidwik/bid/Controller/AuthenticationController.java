package org.bidwik.bid.Controller;

import org.bidwik.bid.Payload.Request.LoginRequest;
import org.bidwik.bid.Payload.Request.RegisterRequest;
import org.bidwik.bid.Payload.Request.VerificationRequest;
import org.bidwik.bid.Payload.Response.LoginResponse;
import org.bidwik.bid.Payload.Response.RegisterResponse;
import org.bidwik.bid.Service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.CREATED);
    }
    
    @PostMapping("/verify")
    public ResponseEntity<LoginResponse> verify(@RequestBody @Valid VerificationRequest verificationRequest){
        return new ResponseEntity<>(authenticationService.verify(verificationRequest), HttpStatus.OK);
    }
}
