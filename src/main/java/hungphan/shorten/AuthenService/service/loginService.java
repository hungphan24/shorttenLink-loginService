package hungphan.shorten.AuthenService.service;

import hungphan.shorten.AuthenService.Entity.Role;
import hungphan.shorten.AuthenService.Entity.TokenEntity;
import hungphan.shorten.AuthenService.Entity.UserEntity;
import hungphan.shorten.AuthenService.Repository.TokenRepository;
import hungphan.shorten.AuthenService.Repository.UserRepository;
import hungphan.shorten.AuthenService.exception.badRequestException;
import hungphan.shorten.AuthenService.exception.userAlreadyExistException;
import hungphan.shorten.AuthenService.payload.request.userRegisterRequest;
import hungphan.shorten.AuthenService.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class loginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtHelper jwtHelper;

    public void registerService(userRegisterRequest request) {
        String email = request.getEmail();
        UserEntity user = userRepository.findByEmail(email);
        if(user != null) {
            throw new userAlreadyExistException("Error: Email already taken");
        }
        UserEntity saveUser = new UserEntity();
        saveUser.setEmail(email);
        saveUser.setPassword(passwordEncoder.encode(request.getPassword()));
        saveUser.setUsername(request.getUsername());
        saveUser.setRole(Role.USER.ordinal());
        userRepository.save(saveUser);
    }

    public String authenService(String authHeader) {
        // final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new badRequestException("Error: Bad request");
        }
        String jwt = authHeader.substring(7);
        TokenEntity storedToken = tokenRepository.findByToken(jwt);
        if(!storedToken.isEnable()) {
            throw new badRequestException("Error: Bad request - token is inactive");
        }

        String email = jwtHelper.decodeToken(jwt);
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return email;
        } else {
            throw new badRequestException("Error: Bad request - User not found");
        }
    }
}
