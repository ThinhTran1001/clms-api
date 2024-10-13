package vn.threeluaclmsapi.service.impl;

import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.Token;
import vn.threeluaclmsapi.repository.TokenRepository;

import java.util.Optional;

@Service
public record TokenService(TokenRepository tokenRepository){

    public int save(Token token){
        Optional<Token> optionalToken = tokenRepository.findByUsername(token.getUsername());
        if(optionalToken.isEmpty()){
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token currentToken = optionalToken.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(currentToken);
            return currentToken.getId();
        }
    }

    public String delete(Token token){
        tokenRepository.delete(token);
        return "Deleted!";
    }

    public Token getByUsername(String username){
        return tokenRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("Token not exist!"));
    }
}
