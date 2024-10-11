package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.SignInRequest;
import vn.threeluaclmsapi.dto.response.TokenResponse;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.repository.UserRepository;
import vn.threeluaclmsapi.service.AuthService;
import vn.threeluaclmsapi.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public TokenResponse authenticate(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }
}
