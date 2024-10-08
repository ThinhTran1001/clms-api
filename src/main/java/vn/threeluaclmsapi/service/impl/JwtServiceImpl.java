package vn.threeluaclmsapi.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.service.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateToken(UserDetails userDetails) {
        return "access-token";
    }
}
