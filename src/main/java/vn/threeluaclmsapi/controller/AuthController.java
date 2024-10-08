package vn.threeluaclmsapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseData<TokenResponse> login(@RequestBody @Valid SignInRequest request) {
        return new ResponseData<>(HttpStatus.OK.toString(), "authenticate", authService.authenticate(request));
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout success!";
    }

    @PostMapping
    public String refreshToken(){
        return "refresh token success!";
    }

}
