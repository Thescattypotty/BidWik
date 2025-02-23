package org.bidwik.bid.Service;

import java.util.Set;

import org.bidwik.bid.Entity.User;
import org.bidwik.bid.EntityRepository.UserRepository;
import org.bidwik.bid.Enum.ERole;
import org.bidwik.bid.IService.IAuthenticationService;
import org.bidwik.bid.Payload.Request.LoginRequest;
import org.bidwik.bid.Payload.Request.RegisterRequest;
import org.bidwik.bid.Payload.Request.VerificationRequest;
import org.bidwik.bid.Payload.Response.LoginResponse;
import org.bidwik.bid.Payload.Response.RegisterResponse;
import org.bidwik.bid.Util.JwtUtil;
import org.bidwik.bid.Util.TwoFactorAuthenticationUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorAuthenticationUtil twoFactorAuthenticationUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
            .firstName(registerRequest.firstName())
            .lastName(registerRequest.lastName())
            .email(registerRequest.email())
            .password(passwordEncoder.encode(registerRequest.password()))
            .phone(registerRequest.phone())
            .is2FAEnabled(false)            
            .roles(Set.of(ERole.ROLE_USER))
            .build();
        if(registerRequest.is2FAEnabled() == true){
            user.set2FAEnabled(true);
            user.setMfaSecret(twoFactorAuthenticationUtil.generateSecret());
        }
        user = userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getAuthorities());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new RegisterResponse(
            accessToken,
            refreshToken,
            user.is2FAEnabled(),
            twoFactorAuthenticationUtil.generateQrCodeUri(user.getMfaSecret(), user.getEmail())
        );
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        User user = userRepository.findByEmail(loginRequest.email())
            .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.is2FAEnabled()){
            return new LoginResponse(
                "",
                "",
                true
            );
        }
        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getAuthorities());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        return new LoginResponse(
            accessToken,
            refreshToken,
            false
        );
    }

    @Override
    public LoginResponse verify(VerificationRequest verificationRequest) {
        User user = userRepository.findByEmail(verificationRequest.email())
            .orElseThrow(() -> new RuntimeException("User not found"));
        if(twoFactorAuthenticationUtil.verifyCode(user.getMfaSecret(), verificationRequest.code())){
            String accessToken = jwtUtil.generateToken(user.getEmail(), user.getAuthorities());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
            return new LoginResponse(
                accessToken,
                refreshToken,
                user.is2FAEnabled()
            );
        }
        throw new RuntimeException("Invalid code");
    }
    
}
