package hungphan.shorten.AuthenService.Controller;

import hungphan.shorten.AuthenService.Entity.TokenEntity;
import hungphan.shorten.AuthenService.Repository.TokenRepository;
import hungphan.shorten.AuthenService.payload.request.userLoginRequest;
import hungphan.shorten.AuthenService.payload.request.userRegisterRequest;
import hungphan.shorten.AuthenService.payload.response.BaseResponse;
import hungphan.shorten.AuthenService.service.Interface.ITokenService;
import hungphan.shorten.AuthenService.service.loginService;
import hungphan.shorten.AuthenService.utils.JwtHelper;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ITokenService iTokenService;

    @Autowired
    private loginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid userLoginRequest request) {
        try {
            String email = request.getEmail();
            String password = request.getPassword();
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
        } catch (Exception e) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(401); // Unauthorized
            errorResponse.setMessage("Invalid credentials");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/logoutt")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            TokenEntity storedToken = tokenRepository.findByToken(jwt);

            if (storedToken != null) {
                storedToken.setEnable(false);
                tokenRepository.save(storedToken);
                SecurityContextHolder.clearContext();

                BaseResponse response = new BaseResponse();
                response.setStatusCode(200);
                response.setMessage("Logout successful");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        BaseResponse errorResponse = new BaseResponse();
        errorResponse.setStatusCode(400);
        errorResponse.setMessage("Invalid token or token not provided");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid userRegisterRequest registerRequest) {
         loginService.registerService(registerRequest);
         BaseResponse baseResponse = new BaseResponse();
         baseResponse.setStatusCode(200);
         baseResponse.setMessage("register succesfully");
         return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/authen")
    @Retry(name = "authen", fallbackMethod = "failResponse")
    public ResponseEntity<?> authen(@RequestBody String authHeader) {
        String email = loginService.authenService(authHeader);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("authentication successfully");
        baseResponse.setData(email);
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> failResponse(Exception ex) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(500);
        baseResponse.setMessage("fallback-response");
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
