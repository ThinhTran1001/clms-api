package vn.threeluaclmsapi.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.threeluaclmsapi.dto.request.SignInRequest;
import vn.threeluaclmsapi.dto.response.TokenResponse;

public interface AuthService {

    TokenResponse authenticate(SignInRequest request);

    TokenResponse refresh(HttpServletRequest request);

    String logout(HttpServletRequest request);

    String forgotPassword(String email);
}
