package hungphan.shorten.AuthenService.service;

import hungphan.shorten.AuthenService.Entity.Role;
import hungphan.shorten.AuthenService.Entity.UserEntity;
import hungphan.shorten.AuthenService.Repository.UserRepository;
import hungphan.shorten.AuthenService.exception.userAlreadyExistException;
import hungphan.shorten.AuthenService.payload.request.userRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class loginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
