package vn.threeluaclmsapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.threeluaclmsapi.model.User;
import vn.threeluaclmsapi.util.enums.TokenType;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String token, TokenType type);

    boolean isValid(String token, UserDetails userDetails, TokenType type);

    String generateRefreshToken(UserDetails userDetails);
}
