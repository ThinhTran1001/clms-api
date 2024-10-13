package vn.threeluaclmsapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.SignInRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.dto.response.TokenResponse;
import vn.threeluaclmsapi.service.AuthService;

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

}
