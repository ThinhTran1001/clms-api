package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.SignInRequest;
import vn.threeluaclmsapi.dto.response.TokenResponse;

public interface AuthService {

    public TokenResponse authenticate(SignInRequest request);
}
