package vn.threeluaclmsapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.SignInRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.dto.response.TokenResponse;
import vn.threeluaclmsapi.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid SignInRequest request) {
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(HttpServletRequest request){
        return new ResponseEntity<>(authService.refresh(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return new ResponseEntity<>(authService.logout(request), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        return new ResponseEntity<>(authService.forgotPassword(email), HttpStatus.OK);
    }

    @GetMapping("google/login")
    public ResponseEntity<Map<String, String>> googleLogin(OAuth2AuthenticationToken authenticationToken) {
        return new ResponseEntity<>(authService.loginByGoogle(authenticationToken), HttpStatus.OK);
    }
}
