package roomescape.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenGenerator tokenGenerator;
    private static final String ROLE_NAME = "ADMIN";
    private static final String TOKEN_NAME = "token";


    public AuthInterceptor(@Autowired TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(TOKEN_NAME))
                .findFirst()
                .map(Cookie::getValue);

        if (token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        AuthCredential authCredential = tokenGenerator.parseAccessToken(token.get());
        if (!authCredential.role().equals(ROLE_NAME)) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
