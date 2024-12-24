package roomescape.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.member.Member;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private AuthService authService;
    private TokenGenerator tokenGenerator;

    public AuthController(
            @Autowired  AuthService authService,
            @Autowired  TokenGenerator tokenGenerator
    ) {
        this.authService = authService;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody LoginRequest memberLoginRequest,
            HttpServletResponse response
    ) {
        Member member = authService.login(memberLoginRequest);

        String tokenValue = tokenGenerator.generateAccessToken(member);

        Cookie cookie = new Cookie("token", tokenValue);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60);
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/check")
    public ResponseEntity loginCheck(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String token = extractTokenFromCookie(cookies);

        TokenInfo tokenInfo = tokenGenerator.decodeAccessToken(token);

        Member member = authService.loginCheck(tokenInfo.id());
        String memberName = member.getName();

        if (!tokenInfo.name().equals(memberName)) {
            throw new RuntimeException("Invalid access token");
        }

        return ResponseEntity
                .ok()
                .body(new LoginCheckResponse(memberName));
    }

    private String extractTokenFromCookie(Cookie[] cookies) {
        try {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }

            return "";
        } catch (NullPointerException e) {
            throw new RuntimeException("abc");
        }
    }
}
