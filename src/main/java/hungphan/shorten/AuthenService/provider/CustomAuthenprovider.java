package hungphan.shorten.AuthenService.provider;

import hungphan.shorten.AuthenService.Entity.UserEntity;
import hungphan.shorten.AuthenService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomAuthenprovider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("authenticate enter " + username + " " + password);

        UserEntity user = userRepository.findByEmail(username);
        if(user != null) {
            System.out.println("user not null");
        } else {
            System.out.println("user null");
        }
        if(passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("password match");
        } else {
            System.out.println("password not match");
        }
        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("authen oke");
            return new UsernamePasswordAuthenticationToken(username, user.getPassword(), new ArrayList<>());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
