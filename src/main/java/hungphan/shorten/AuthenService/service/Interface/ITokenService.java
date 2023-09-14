package hungphan.shorten.AuthenService.service.Interface;

import hungphan.shorten.AuthenService.Entity.TokenEntity;

public interface ITokenService {

    public TokenEntity findByToken(String token);
}
