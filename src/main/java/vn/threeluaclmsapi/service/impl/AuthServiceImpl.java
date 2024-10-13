package vn.threeluaclmsapi.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.SignInRequest;
import vn.threeluaclmsapi.dto.response.TokenResponse;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.User;
import vn.threeluaclmsapi.repository.UserRepository;
import vn.threeluaclmsapi.service.AuthService;
import vn.threeluaclmsapi.service.JwtService;

import java.util.Optional;

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

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        String token = request.getHeader("x-token");
        if(StringUtils.isBlank(token)){
            throw new CommonException("Token must be not blank!");
        }
        final String userName = jwtService.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(userName);
        if(!jwtService.isValid(token, user.get())){
            throw new CommonException("Token is invalid!");
        }
        String accessToken = jwtService.generateToken(user.get());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .userId(user.get().getId())
                .build();
    }
}
