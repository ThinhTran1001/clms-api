package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.threeluaclmsapi.model.Token;
import vn.threeluaclmsapi.model.User;
import vn.threeluaclmsapi.repository.UserRepository;
import vn.threeluaclmsapi.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private static final String ALLOWED_EMAIL_DOMAIN = "fpt.edu.vn";

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        if(!isAllowedDomain(email)){
            throw new OAuth2AuthenticationException("Invalid email domain: " + email);
        }

        userRepository.findByEmail(email)
                .orElseThrow(() -> new OAuth2AuthenticationException("No user found with this email: " + email));

        return oAuth2User;
    }

    private boolean isAllowedDomain(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        String domain = email.substring(email.indexOf("@") + 1);
        return ALLOWED_EMAIL_DOMAIN.equals(domain);
    }
}
