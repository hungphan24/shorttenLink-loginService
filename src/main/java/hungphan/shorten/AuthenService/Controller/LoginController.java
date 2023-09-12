package hungphan.shorten.AuthenService.Controller;

import hungphan.shorten.AuthenService.Entity.TokenEntity;
import hungphan.shorten.AuthenService.Repository.TokenRepository;
import hungphan.shorten.AuthenService.payload.response.BaseResponse;
import hungphan.shorten.AuthenService.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password) {
        System.out.println("api signin enter " + email + " " + password);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(user);

        String token = jwtHelper.generateToken(email);
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setEnable(true);
        tokenRepository.save(tokenEntity);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setData(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
