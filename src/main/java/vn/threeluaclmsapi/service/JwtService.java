package vn.threeluaclmsapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.threeluaclmsapi.model.User;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isValid(String token, UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);
}
