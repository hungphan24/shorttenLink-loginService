package hungphan.shorten.AuthenService.service;

import hungphan.shorten.AuthenService.Entity.TokenEntity;
import hungphan.shorten.AuthenService.Repository.TokenRepository;
import hungphan.shorten.AuthenService.service.Interface.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements ITokenService {
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public TokenEntity findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
